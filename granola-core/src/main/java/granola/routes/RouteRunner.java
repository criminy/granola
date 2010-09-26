package granola.routes;

import granola.annotations.meta.ParameterAnnotation;
import granola.annotations.meta.ParameterProcessor;
import granola.config.Configuration;
import granola.exceptions.NotFoundException;
import granola.functional.Callable;
import granola.internal.ControllerInstantiator;
import granola.internal.RunHook;
import granola.mvc.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Internal utility class for the running of the Routes.
 * 
 * @author criminy
 *
 */
public class RouteRunner {
	
	/**
	 * Tests if the given URL matches the URL pattern given.
	 * @param url The URL
	 * @param pattern The pattern
	 * @return True if matches, False otherwise.
	 */
	public static boolean isUrlMatching(String url, String pattern)
	{
		String regexp = pattern.replaceAll("\\{[a-zA-Z]+\\}","(?!/).+");
		return url.matches(regexp);					
	}
	
	/**
	 * Tests if the given HTTP method is supported by the 
	 * given Route.
	 * @param method The HTTP method as a String
	 * @param route The Route instance being invoked.
	 * @return True if the method is supported, False otherwise.
	 */
	public static boolean isMethodMatching(String method, Route route)
	{
		return route.httpMethod.equalsIgnoreCase(method);
	}
	
	/**
	 * Gets the request parameters and path variables out of the URL.
	 * 
	 * @param r The route we are invoking.
	 * @param url The URL being requested.
	 * @return A key-value object where the keys are the variable names and
	 *  the values are the values.
	 */
	public static Map<String,Object> getRequestParameters(Route r, String url,Map<Object,Object> inputParams)
	{		
		String pattern = r.url;
		List<Object> paramValues = new java.util.LinkedList<Object>();
		
		Map<String,Object> params = new java.util.HashMap<String, Object>();
		{
			String regexp = pattern.replaceAll("\\{[a-zA-Z]+\\}","((?!/).+)");		
			Pattern p = Pattern.compile(regexp);
			Matcher m = p.matcher(url);	
			
			if(m.find() && m.groupCount() != 0 ) 
				for(int i = 1; i<=m.groupCount(); i++)
					paramValues.add(m.group(i));				
				
		}
		
		List<String> paramKeys = new java.util.LinkedList<String>();
		{			
			String regexp = pattern.replaceAll("\\{([a-zA-Z]+)\\}","\\\\{((?!/).+)\\\\}");
			Pattern p = Pattern.compile(regexp);
			Matcher m = p.matcher(pattern);
			
			if(m.find() && m.groupCount() != 0 ) 
				for(int i = 1; i<=m.groupCount(); i++)	{
					String g = m.group(i);				
					paramKeys.add(g);					
				}
		}

		for(int idx = 0;idx!=paramKeys.size();idx++)
		{			
			if(paramValues.get(idx) != null)
				params.put(paramKeys.get(idx),paramValues.get(idx));
		}
		
		for(String k : r.defaultValues.keySet())
		{
			params.put(k,r.defaultValues.get(k));
		}
		
		for(Object k : inputParams.keySet())
		{
			params.put((String) k,inputParams.get(k));
		}
		
		return params;
	}
	
	/**
	 * Method for invoking the URL with the given HTTP method,
	 * implementation-specific RunHook, and Configuration.
	 * 
	 * @param routes The list of routes to look through.
	 * @param method The HTTP method being invoked
	 * @param url The request URL
	 * @param runHook The implementation specific runhook.
	 * @param config The User-defined configuration.
	 * @throws Throwable On an exception.
	 */
	public static void runWithUrl(List<Route> routes, String method, String url,RunHook runHook,Configuration config,Map<Object,Object> inputParams) throws Throwable
	{
		for(Route r : routes)
		{
			if(isMethodMatching(method, r) && isUrlMatching(url,r.url))
			{
				run(r,getRequestParameters(r,url, inputParams),runHook, config);
				return;
			}
		}
		// NOTE:
		// 	when translating exceptions to HTTP responses,
		// 	we usually piggy-back on the current Controller and Route.
		//  However, when we get here there are no current Controllers
		//  or Routes to use.
		Route r = config.getCoreController();
		Controller o = ControllerInstantiator.getController(r,getRequestParameters(r,url,inputParams),runHook);
		o = runHook.preInvoke(null, o,null);
		config.getExceptionMappings().get(NotFoundException.class).call(o);
	}
	
	/**
	 * Translates the ParameterAnnotation
	 * specifications on the View methods to 
	 * their required values.
	 * 
	 * @param route The route being invoked.
	 * @param o The controller instance
	 * @return The list of arguments to call on the Method.
	 */
	public static List<Object> wireArgs(Route route,Controller o)
	{
		List<Object> args = new java.util.ArrayList<Object>();
		for(int i = 0; i!=route.javaMethod.getParameterAnnotations().length;i++)
		{			
			for(Annotation a : route.javaMethod.getParameterAnnotations()[i])
			{			
				if(a.annotationType().isAnnotationPresent(ParameterAnnotation.class))
				{
					ParameterAnnotation paramMeta = a.annotationType().getAnnotation(ParameterAnnotation.class);
					try {
						Class<?> t = route.javaMethod.getParameterTypes()[i];
						@SuppressWarnings("unchecked")
						ParameterProcessor<Annotation,?> paramProcessor = ((ParameterProcessor<Annotation,?>)paramMeta.value().newInstance());												
						args.add(paramProcessor.process(a,t, route.javaMethod, o));
					} catch (Exception e) {
						throw new RuntimeException(e);
					} 
					break;
				}
			}
		}
		return args;
	}
	
	

	/**
	 * Internal run method for invoking the route 
	 * with the given Variables, RunHook, and Configuration.
	 * 
	 * @param route The route being invoked.
	 * @param variables The path variables and request parameters of the URL.
	 * @param runHook The implementation-specific runHook.
	 * @param config The configuration.
	 * @throws Throwable on an exception.
	 */
	public static void run(Route route,Map<String,Object> variables,RunHook runHook,Configuration config) throws Throwable
	{		
		List<Object> args = new java.util.ArrayList<Object>();
		Controller o = ControllerInstantiator.getController(route,variables,runHook);	
		
		args = wireArgs(route,o);
		
		try {			
			o = runHook.preInvoke(route.javaMethod,o,args.toArray());
			route.javaMethod.invoke(o,args.toArray());	
		} catch (InvocationTargetException e) {
			Throwable exn = e.getCause();
			
			Callable<Controller> c = config.getExceptionMappings().get(exn.getClass());
			if(c != null)
				c.call(o);
			else
				throw exn;
		}
	}
}


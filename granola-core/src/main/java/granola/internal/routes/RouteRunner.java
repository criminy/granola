package granola.internal.routes;

import granola.annotations.meta.ParameterAnnotation;
import granola.annotations.meta.ParameterProcessor;
import granola.mvc.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RouteRunner {
	
	public static boolean isUrlMatching(String url, String pattern)
	{
		String regexp = pattern.replaceAll("\\{[a-zA-Z]+\\}","(?!/).+");
		return url.matches(regexp);					
	}
	
	public static boolean isMethodMatching(String method, Route route)
	{
		return route.httpMethod.equalsIgnoreCase(method);
	}
	
	public static Map<String,Object> getRequestParameters(Route r, String url)
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
		
		
			
		return params;
	}
	
	public static void runWithUrl(List<Route> routes, String method, String url,RunHook runHook)
	{
		System.out.println("URL : " + url);
		for(Route r : routes)
		{
			System.out.println(r + " " + r.url);
			if(isMethodMatching(method, r) && isUrlMatching(url,r.url))
			{
				run(r,getRequestParameters(r,url),runHook);
				return;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void run(Route route,Map<String,Object> variables,RunHook runHook)
	{		
		List<Object> args = new java.util.ArrayList<Object>();
		Controller o = ControllerInstantiator.getController(route,variables,runHook);	
		
		for(int i = 0; i!=route.javaMethod.getParameterAnnotations().length;i++)
		{			
			for(Annotation a : route.javaMethod.getParameterAnnotations()[i])
			{			
				if(a.annotationType().isAnnotationPresent(ParameterAnnotation.class))
				{
					ParameterAnnotation paramMeta = a.annotationType().getAnnotation(ParameterAnnotation.class);
					try {
						Class t = route.javaMethod.getParameterTypes()[i];
						ParameterProcessor<Annotation,?> paramProcessor = ((ParameterProcessor<Annotation,?>)paramMeta.value().newInstance());												
						args.add(paramProcessor.process(a,t, route.javaMethod, o));
					} catch (Exception e) {
						throw new RuntimeException(e);
					} 
					break;
				}
			}
		}
		
		try {			
			o = runHook.preInvoke(route.javaMethod,o,args.toArray());			
			route.javaMethod.invoke(o,args.toArray());			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


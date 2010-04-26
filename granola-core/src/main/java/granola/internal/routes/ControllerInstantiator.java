package granola.internal.routes;

import granola.mvc.Controller;
import granola.mvc.StandardController;

import java.util.Map;


public class ControllerInstantiator {

	public static Controller getController(Route route,Map<String,Object> variables, RunHook hook)
	{
		if(StandardController.class.isAssignableFrom(route.javaMethod.getDeclaringClass()))
		{
			StandardController o;
			try {
				o = (StandardController) route.javaMethod.getDeclaringClass().newInstance();
				return hook.onInstantiate(o, variables);											
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}else{
			throw new UnsupportedOperationException("Non- StandardControllers not yet supported!");
		}
		

	}
	
}

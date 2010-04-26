package granola.internal.routes;

import granola.mvc.Controller;

import java.lang.reflect.Method;
import java.util.Map;

public interface RunHook {

	public Controller preInvoke(Method m, Controller o, Object[] args);
	public Controller onInstantiate(Controller o,Map<String,Object> parameters);
}

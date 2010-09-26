package granola.config;

import granola.exceptions.NotFoundException;
import granola.functional.Callable;
import granola.internal.CoreController;
import granola.mvc.Controller;
import granola.routes.LocalNameFileLoadingRouteFactory;
import granola.routes.Route;
import granola.routes.RouteFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Callable which sets HTTP 404
 * and loads 404.html file.
 * 
 * @author criminy
 *
 */
class FourOhFourError implements Callable<Controller>
{

	@Override
	public void call(Controller t) {
		t.response().set_response_code(404);
		t.response().body().from_file("404.html");
	}
	
}

/**
 * Abstract class for providing web configuration.
 * 
 * @author criminy
 *
 */
public abstract class Configuration {

	private RouteFactory routeFactory = new LocalNameFileLoadingRouteFactory();
	private Map<Class<? extends Throwable>,Callable<Controller>> exceptionMappings = 
		new HashMap<Class<? extends Throwable>,Callable<Controller>>();
	
	/**
	 * Method to implement when providing custom configuration.
	 */
	public abstract void setup();
	

	/**
	 * Final method which is called by the granola-core system
	 * when configuration is initially required.
	 */
	public final void doSetup()
	{
		addExceptionTranslation(NotFoundException.class,new FourOhFourError());
		
		setup();
	}
	
	/**
	 * Adds a Controller Callable function to be called when
	 * any view throws the given exception.
	 *  
	 * @param thr The exception to translate.
	 * @param c The function to call.
	 */
	public void addExceptionTranslation(Class<? extends Throwable> thr,Callable<Controller> c)
	{
		//TODO: assert or throw exception on conflicts.
		exceptionMappings.put(thr,c);
	}
	
	/**
	 * Set the route factory, which loads the Route
	 * objects to fully understand how to convert HTTP requests
	 * to java code. 
	 *  
	 * @param routeFactory
	 * @see RouteFactory
	 */
	public void setRouteFactory(RouteFactory routeFactory) {
		this.routeFactory = routeFactory;
	}

	public RouteFactory getRouteFactory() {
		return routeFactory;
	}


	public Route getCoreController(){
		Route r = new Route();
		r.httpMethod = "GET";
		try {
			r.javaMethod = CoreController.class.getMethod("view");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.url = "/granola_core_servlet";
		return r;
	}

	public Map<Class<? extends Throwable>, Callable<Controller>> getExceptionMappings() {
		return exceptionMappings;
	}
}

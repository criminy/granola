package granola.mvc;

import java.util.Map;

/**
 * Interface for grabbing HTTP Request and Response 
 * information.
 * 
 * @author criminy
 * @see ServletControler
 * @see StandardController
 *
 */
public interface Controller {

	public Map<String, Object> parameters();

	public Response response();
	public Request request();
}

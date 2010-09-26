package granola.mvc;


/**
 * HTTP Response interface for 
 * getting and setting response information.
 * 
 * @author criminy
 *
 */
public interface Response {

	/**
	 * Set the HTTP response code.
	 * @param code The http respones code.
	 */
	public void set_response_code(int code);
	
	/**
	 * Sets an HTTP response header to the given value.
	 * @param header The header name
	 * @param value The value
	 */	
	public void set_header(String header,String value);
	
	/**
	 * Gets the response body.
	 * @return The ResponseBody object.
	 */
	public ResponseBody body();
	
	/**
	 * Sends an HTTP Redirect to the given Class.viewName with the given arguments.
	 * 
	 * @param <T> The Controller implementation.
	 * @param c The reference to the Class.
	 * @param viewName The name of the function.
	 * @param arguments The arguments to the function.
	 */
	public <T extends Controller> void redirect(Class<T> c,String viewName,String arguments);
	
	/**
	 * Sends an HTTP redirect to an arbitrary URL.
	 * 
	 * @param url The url to redirect to.
	 */
	public void redirect(String url);
	
	/**
	 * Gets the internal Response object, which is
	 * different depending on the HTTP backend.
	 * 
	 * @return The internal object.
	 */
	public Object internal();
}
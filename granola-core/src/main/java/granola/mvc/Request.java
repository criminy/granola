package granola.mvc;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * HTTP request object for getting request information.
 * 
 * @author criminy
 *
 */
public interface Request {
	public Map<String,Object> headers();
	public Map<String, List<Object>> data();
	
	public InputStream input();
	
	public Object internal();
}
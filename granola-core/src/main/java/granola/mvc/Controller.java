package granola.mvc;

import java.util.Map;

public interface Controller {

	public Map<String, Object> parameters();

	public Response response();
	public Request request();
}

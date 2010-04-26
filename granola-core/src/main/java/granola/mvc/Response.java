package granola.mvc;


public interface Response {

	public void set_response_code(int code);
	public void set_header(String header,String value);
	public ResponseBody body();
	
	public <T extends Controller> void redirect(Class<T> c,String viewName,String arguments);
	
	public Object internal();
}
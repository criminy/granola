package granola.servlet;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import granola.mvc.Controller;
import granola.mvc.Response;
import granola.mvc.ResponseBody;

class HttpResponse implements Response {
	
	@Override
	public void set_response_code(int code) {
		body.response.setStatus(code);
	}
	
	@Override
	public Object internal() {
		return this.body.response;
	}
	
	Map<String,Object> headers = new java.util.HashMap<String, Object>();
	HttpResponseBody body;
	public HttpResponse(HttpServletResponse response,OutputStream responseOutputStream) 
	{	
		body = new HttpResponseBody();
		body.response = response;
		body.responseOutputStream = responseOutputStream;	
	}
	
	@Override
	public ResponseBody body() {
		return body;
	}
	@Override
	public void set_header(String header,String value)
	{
		body.response.setHeader(header, value);
	}
	@Override
	public <T extends Controller> void redirect(Class<T> c, String viewName,
			String arguments) {
		//TODO: implements
		throw new NotImplementedException();
	}
}

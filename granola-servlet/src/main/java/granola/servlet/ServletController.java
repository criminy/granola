package granola.servlet;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import granola.mvc.Controller;
import granola.mvc.Request;
import granola.mvc.Response;

class ServletController implements Controller{

	public void setup(HttpServletRequest request, HttpServletResponse response,OutputStream responseOutputStream)
	{
		this.request = new HttpRequest(request);
		this.response = new HttpResponse(response,responseOutputStream);
		
	}
	Response response;
	Request request;
	
	Map<String,Object> _parameters;
	
	@Override
	public Map<String, Object> parameters() {
		return _parameters;
	}
	
	@Override
	public Request request() {
		return this.request;
	}
	
	@Override
	public Response response() {
		return this.response;
	}
	
	
}

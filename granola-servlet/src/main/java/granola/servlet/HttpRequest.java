package granola.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import granola.mvc.Request;

import static granola.internal.util.iterators.Iterators.enumeration_iter;

/**
 * Servlet implementation of the Request object.
 * 
 * @author criminy
 *
 */
class HttpRequest implements Request
{
	
	HttpServletRequest request;
	Map<String,Object> headers;
	Map<String,List<Object>> data;
	InputStream requestInputStream;
	
	public Object internal() {
		return request;
	}
	
	public InputStream input() {
		return requestInputStream;
	}
	
	public HttpRequest(HttpServletRequest request) {
		this.request = request;
		try {
			this.requestInputStream = request.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.headers = new java.util.HashMap<String, Object>();		
		{ //setup headers
			for(String headerName : enumeration_iter(String.class,this.request.getHeaderNames()))
			{//TODO: support multiple header values				
				headers.put(headerName, this.request.getHeader(headerName));
			}
		}
		
		this.data = new java.util.HashMap<String,List<Object>>();
		{ // setup parameter data
			for(String paramName : enumeration_iter(String.class,this.request.getParameterNames()) ) {
				this.data.put(paramName,new java.util.LinkedList<Object>());
				for(String paramValue : this.request.getParameterValues(paramName))
				{
					this.data().get(paramName).add(paramValue);
				}
			}
		}
	}
	
	public Map<String, List<Object>> data() {
		return this.data;
	}
	
	public Map<String, Object> headers() {
		return this.headers;
	}
}


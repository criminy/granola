package granola.servlet;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import granola.mvc.Controller;
import granola.mvc.StandardController;
import granola.internal.routes.RunHook;

class ServletRunHook implements RunHook
{
	OutputStream os;
	HttpServletRequest request;
	HttpServletResponse response;
	public ServletRunHook(OutputStream os,HttpServletRequest request,HttpServletResponse response) {
		this.os = os;
		this.request = request;
		this.response = response;
		
	}
	
	
	@Override
	public Controller onInstantiate(Controller o,Map<String,Object> parameters) {
		Controller c = (Controller) o;
		if(c != null && StandardController.class.isAssignableFrom(c.getClass()))
		{
			ServletController servlet = new ServletController();
			servlet.setup(request, response,os);
			servlet._parameters = parameters;
			((StandardController)c).setController(servlet);
			return c;
		}else if( c == null) {
			//TODO: create a mixin of the user-made abstract Controller impl
			//	 and the ServletController object
			throw new UnsupportedOperationException();
		}
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Controller preInvoke(Method m,Controller o, Object[] args) {
		return o;
	}
}
package granola.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import granola.config.Configuration;
import granola.config.SimpleConfiguration;
import granola.routes.InputStreamRoutesFactory;
import granola.routes.Route;
import granola.routes.RouteFactory;
import granola.routes.RouteRunner;

import static granola.internal.util.reflection.ReflectionUtilities.*;

/**
 * Servlet which wires the granola core functionality
 * to a servlet context.
 * 
 * @author criminy
 *
 */
public class SimpleGranolaServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private List<Route> routes =new java.util.ArrayList<Route>();
	private Configuration configuration = new SimpleConfiguration();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String classname = config.getInitParameter("granola.configuration.class");
		
		if(classname != null)
		{
			configuration = runtimeCreateInstance(classname,ServletException.class);
		}
		
		configuration.doSetup();			
		RouteFactory fact = configuration.getRouteFactory();
		URL routesUrl = SimpleGranolaServlet.class.getClassLoader().getResource(
				config.getServletName() + "-" +	"routes.r");			
		routes = fact.getRoutes(routesUrl.getFile());		
		try {
			routes.addAll(
				new InputStreamRoutesFactory().getRoutes(
					(SimpleGranolaServlet.class.getClassLoader().getResource(
				"granola/core/granola-core-routes.r").openStream())));
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}
	
	
	protected void run(HttpServletRequest req, HttpServletResponse resp,String method) throws IOException, ServletException
	
	{
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			pathInfo = "/";
		}		
		OutputStream os = resp.getOutputStream();
		
		try {
			RouteRunner.runWithUrl(routes,method, pathInfo, new ServletRunHook(os,req,resp), configuration,new ParameterMapProxy(req));
		} catch (Throwable e) {
			throw new ServletException(e);
		}finally{
			os.close();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		run(req,resp,"GET");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		run(req,resp,"POST");
	}
}


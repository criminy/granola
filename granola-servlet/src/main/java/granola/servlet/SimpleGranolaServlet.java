package granola.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import granola.internal.routes.Route;
import granola.internal.routes.RouteRunner;
import granola.internal.routes.RoutesParser;

public class SimpleGranolaServlet extends HttpServlet
{


	private static final long serialVersionUID = 1L;
	private List<Route> routes =new java.util.ArrayList<Route>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			URL routesUrl = RoutesParser.class.getClassLoader().getResource(
					config.getServletName() + "-" +	"routes.r");			
			RoutesParser.loadRoutes(routes,routesUrl.toURI());
		} catch (IOException e) {
			throw new ServletException(e);
		} catch (URISyntaxException e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			pathInfo = "/";
		}		
		OutputStream os = resp.getOutputStream();
		RouteRunner.runWithUrl(routes,"GET", pathInfo, new ServletRunHook(os,req,resp));
		os.close();		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			pathInfo = "/";
		}
		OutputStream os = resp.getOutputStream();		
		RouteRunner.runWithUrl(routes,"POST", pathInfo, new ServletRunHook(os,req,resp));
		os.close();		
	}
	
	
	
}


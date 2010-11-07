package granola.mvc;

import java.util.Map;

/**
 * This is a Controller implementation that
 * is a proxy between a container provided Controller implementation
 * and the extended class.
 * @author criminy
 *
 */
public class StandardController implements Controller
{

	private Controller controller;
	public void setController(Controller c)
	{
		controller = c;
	}
	
	public Map<String, Object> parameters() {
		return controller.parameters();
	}

	public Request request() {
		return controller.request();
	}

	public Response response() {
		return controller.response();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private TemplateEngine templateEngine;
	
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public void render(String file)
	{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);

		try {
			templateEngine.render(is,this.os);
		}catch(IOException exn)
		{
			exn.printStackTrace();
			//report error using impl-specific class
		}
	}
	
	public Map<String,Object> context(){
		return templateEngine.getContext();
	}
	
	
	Route route;
	
	public void setRoute(Route route) {
		this.route = route;
	}
	public Route route() {
		return route;
	}
	

	private Map<String, List<Object>> data = 
		new HashMap<String, List<Object>>();
	

	private Map<String,Object> parameters;
	
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public Map<String, List<Object>> data() {
		return data;
	}
	
	private OutputStream os;
	
	public void setOs(OutputStream os) {
		this.os = os;
	}
	*/
	
	
	 
}

package granola.mvc.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Simple interface for creating or wiring in third party template
 * engines.
 * 
 * @author criminy
 *
 */
public interface TemplateEngine {

	public Map<String,Object> getContext();
	
	public void render(InputStream is, OutputStream os) throws IOException;
}

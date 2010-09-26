package granola.mvc.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sheenobu.django.template.*;

/**
 * Implementation of TemplateEngine
 * that uses the net.sheenobu.django.template
 * project.
 * 
 * @author criminy
 *
 */
public class DjangoStyleTemplateEngine implements TemplateEngine{

	TemplateLoader loader = new ClasspathTemplateLoader();
	ContextFactory factory = new ContextFactory(); 
	Context ctx = factory.newContext();
	
	@Override
	public Map<String, Object> getContext() {
		return ctx.asMap();
	}
	
	@Override
	public void render(InputStream is, OutputStream os) throws IOException {
		Template t= new Template(loader,is);		
		t.render(ctx, os);
	}
	
}

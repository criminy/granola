package granola.mvc.template;

import granola.template.context.Context;
import granola.template.context.GranolaContext;
import granola.template.exec.TemplateRunner;
import granola.template.model.Template;
import granola.template.parser.TemplateParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * Implementation of TemplateEngine
 * that uses the net.sheenobu.django.template
 * project.
 * 
 * @author criminy
 *
 */
public class DjangoStyleTemplateEngine implements TemplateEngine{

	GranolaContext granolaCtx = new GranolaContext();
	TemplateParser parser = new TemplateParser();
	TemplateRunner runner = new TemplateRunner();
	//TemplateLoader loader = new ClasspathTemplateLoader();
	//ContextFactory factory = new ContextFactory(); 
	Context ctx = new Context();
	
	public Map<String, Object> getContext() {
		return ctx.asMap();
	}
		
	public void render(InputStream is, OutputStream os) throws IOException {
		Template t= parser.fromString(granolaCtx, IOUtils.toString(is));	
		OutputStreamWriter wr = new OutputStreamWriter(os);
		
		runner.runTemplate(granolaCtx, wr, t, ctx);
		wr.flush();
		wr.close();
	}
	
}

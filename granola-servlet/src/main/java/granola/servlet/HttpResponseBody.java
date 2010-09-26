package granola.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import granola.mvc.ResponseBody;
import granola.mvc.template.DjangoStyleTemplateEngine;
import granola.mvc.template.TemplateEngine;

/**
 * Servlet implementation of the Response body.
 * 
 * @author criminy
 *
 */
class HttpResponseBody implements ResponseBody {
	
	HttpServletResponse response;	
	OutputStream responseOutputStream;
	//TemplateEngine engine = new SimpleTemplateEngine();
	TemplateEngine engine = new DjangoStyleTemplateEngine();
	
	@Override
	public Map<String, Object> context() {
		return engine.getContext();
	}
	@Override
	public void from_file(String filename) {
		
		try {			
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);			
			engine.render(is, responseOutputStream);		
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	@Override
	public void from_stream(InputStream is){
		
		try {
			for(int i = is.read();i!=-1; i = is.read())
			{
				responseOutputStream.write(i);
			}
		}catch(IOException exn)
		{
			throw new RuntimeException(exn);
		}
	}
	@Override
	public void from_string(String content) {		
		try {
			Writer wr = new OutputStreamWriter(responseOutputStream);
			wr.write(content);
			wr.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
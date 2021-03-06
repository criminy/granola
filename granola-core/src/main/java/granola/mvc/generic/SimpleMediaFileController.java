package granola.mvc.generic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import granola.exceptions.NotFoundException;
import granola.mvc.StandardController;
import granola.mvc.annotations.Parameter;

/**
 * A controller for exposing static media files
 * in a given directory
 *
 * Example:
 *    GET /media/{file} granola.mvc.generic.SimpleMediaFileController.serve root=media
 */
public class SimpleMediaFileController
	extends StandardController
{
	
	public static Map<String,String> contentTypes;
	static {
		contentTypes = new HashMap<String,String>();
		contentTypes.put(".+css$","text/css");
		contentTypes.put(".+html$","text/html");
		contentTypes.put(".+xml$","text/xml");
		contentTypes.put(".+jpg$","image/jpg");
		contentTypes.put(".+png$","image/png");
		contentTypes.put(".+jpeg$","image/jpeg");
		contentTypes.put(".+ico$","image/icon");
		contentTypes.put(".+js$","application/javascript");		
	}
		
	public void serve(
		@Parameter("root") String root,
		@Parameter("file") String str_file) throws NotFoundException
	{
		if(root == null) root = "";
		
		str_file = root + "/" + str_file;
		
		InputStream file_is = SimpleMediaFileController.class.getClassLoader()
			.getResourceAsStream(str_file);
		if(file_is == null)
		{
			throw new NotFoundException();
		}
		
		this.response().set_header("Content-Type","text/plain");
		for(String key : contentTypes.keySet())
		{
			if(str_file.matches(key))
			{
				this.response().set_header("Content-Type",contentTypes.get(key));
			}
		}
		
		response().body().from_stream(file_is);	
	}
	
	
}


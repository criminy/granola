package granola.mvc;

import java.io.InputStream;
import java.util.Map;

public interface ResponseBody {
	public void from_stream(InputStream is);
	public void from_string(String content);
	public void from_file(String filename);
	public Map<String,Object> context();
}
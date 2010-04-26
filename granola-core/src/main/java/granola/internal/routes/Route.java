package granola.internal.routes;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Route {

	public String url;
	
	public String httpMethod;
	
	public Method javaMethod;
	
	public Map<String,Object> defaultValues = 
		new java.util.HashMap<String, Object>();
	
	public Map<String,Integer> parameterKeys = 
		new java.util.HashMap<String, Integer>();
	
	@Override
	public String toString() {
		return 
			String.format("%s %s %s.%s %s",
				httpMethod,
				url,
				javaMethod.getDeclaringClass().getCanonicalName(),
				javaMethod.getName(),
				mapToString(defaultValues,","));				
	}
	
	public static String mapToString(
			Map<?,?> m,
			String token)
	{
		String str = "";
		for(Object keys : m.keySet()) {
			str += keys + "=" + m.get(keys) + token;
		}
		if(str.length() > 0) 
			return str.substring(0,str.length()-token.length());
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static void stringToMap(			
			String input,
			Map m,
			String token)
	{
		for(String s : input.split(token))
		{			
			String key = s.split("=")[0];
			String val = s.split("=")[1];
			m.put(key,val);
		}
	}

	
	@SuppressWarnings("unchecked")
	public static void listPairsToMap(			
			List<String> input,
			Map m)
	{
		for(String s :input)
		{		
			String key = s.split("=")[0].trim();
			String val = s.split("=")[1].trim();
			
			m.put(key,val);
		}
	}
}
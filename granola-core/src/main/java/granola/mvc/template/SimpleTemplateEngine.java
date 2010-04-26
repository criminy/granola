package granola.mvc.template;


import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.HashMap;
import java.util.Map;

interface ObjectLookup {
	public Object get(String key);
}

class ContextObjectLookup implements ObjectLookup{
	@Override
	public Object get(String key) {
		return ctx.get(key);
	}
	public ContextObjectLookup(Map<String,Object> ctx) {
		this.ctx = ctx;
	}
	Map<String,Object> ctx;
}

class SelfObjectLookup implements ObjectLookup {
	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}

class TemplateEngineTester {
	
	public static String from_string(TemplateEngine engine,String str) throws IOException
	{		
		InputStream is = new ByteArrayInputStream(str.getBytes());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		engine.render(is, baos);
		
		return baos.toString();
	}
	
	public static String from_file(TemplateEngine engine,String str) throws IOException
	{		
		InputStream is = SimpleTemplateEngine.class.getClassLoader().getResourceAsStream(str);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.out.println(is);
		engine.render(is, baos);
		
		return baos.toString();
	}
	
	public static void main(String[] args) throws IOException
	{
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		engine.getContext().put("variable","X");
		engine.getContext().put("variable2","Y");
		
		
	//	System.out.println(from_string(engine,"asd_{% variable %}_asdji{% variable %} {% variable2 %}"));
	//	System.out.println(from_string(engine,
	//			"{{ block content }} aksjsad {{ endblock }} _-_-_{{ reference content }}_-_-_"));
		
		System.out.println("----------");
		System.out.println(from_file(engine,"sample.html"));
		System.out.println("----------");
	}
}


public class SimpleTemplateEngine implements TemplateEngine{
	
	Map<String,String> blocks = new java.util.HashMap<String, String>();
	Map<String,Object> context = new HashMap<String, Object>();
	String parentTemplateFile = null;
	
	@Override
	public Map<String, Object> getContext() {
		return context;
	}
	@Override
	public void render(InputStream is, OutputStream os) throws IOException {
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os));
		for(int i = is.read(); i>=0; i=is.read())
		{
			if(i == (int)'{')
			{
				if(!handleReference(is,wr))
				{
					wr.write(i);
				}
			}else{
				wr.write(i);
			}
		}	
		
		
		if(parentTemplateFile != null)
		{
			InputStream fis = SimpleTemplateEngine.class.getClassLoader().getResourceAsStream(parentTemplateFile);			
			parentTemplateFile = null;
			render(fis,os);
		}
		wr.close();
	}	
	
	protected boolean handleReference(InputStream is, BufferedWriter os) throws IOException
	{
		int n = is.read();
		if(n == (int)'{')
		{
			String buffer = readUntil(is,"}}").trim();		
		
			//String parts[] = buffer.split(".");
			//first part is always on the context.
				// second part is attached to the first part.
			
			Object val= context.get(buffer);
			if(val != null)
				os.write(context.get(buffer).toString());
			return true;
		}else if(n == (int) '%') {
			String buffer = readUntil(is,"%}").trim();
			
			if(buffer.matches(".*block .+.*"))
			{
				String block = readUntil(is,"{%");				
				readUntil(is,"%}");
				blocks.put(buffer.split(" ")[1],block);
			}
			if(buffer.matches(".*reference .+.*"))
			{
				String name = buffer.split(" ")[1];
				os.write(blocks.get(name).trim());
			}
			if(buffer.matches(".*extend .+.*"))
			{
				String templateReference = buffer.split(" ")[1];				
				parentTemplateFile = templateReference;
			}
			return true;
		}else{
			os.write(n);
			return false;
		}
	}
	
	
	
	public static String readUntil(InputStream is, int ch) throws IOException
	{
		String b = "";
		for(int x = is.read(); (x != ch && x >= -1) ; x = is.read())
		{
			b += (char)x;
		}
		return b;
	}
	
	public static String readUntil(InputStream is, String ch) throws IOException
	{
		int idx = 0;
		byte[] a = ch.getBytes();
		
		String buffer = "";
		while(idx < a.length)
		{
			buffer += readUntil(is,a[idx]);
			idx++;
		}
		return buffer;
	}
	 
	
}

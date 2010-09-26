package granola.example.simple;

import granola.mvc.StandardController;
import granola.mvc.annotations.Paginate;
import granola.mvc.annotations.Parameter;

public class SimpleController extends StandardController
{

	public void plain()
	{
		response().set_header("Content-Type","text/plain");	
		response().body().from_string("Hello World");
	}
	
	public void xml(@Parameter("template") String template)
	{
		response().set_header("Content-Type","text/xml");				
		response().body().from_file(template);
	}

	public void template(@Parameter("template") String template) 			
	{
		response().set_header("Content-Type","text/html");
		response().body().context().put("message","Hello World!");
		response().body().from_file(template);
	}
	
	public void fruits(
			@Parameter("template") String template,
			@Paginate(value="fruit",list=GetFruitList.class) Fruit fruit) 			
	{
		response().body().context().put("True",true);
		response().body().context().put("False",false);
		response().set_header("Content-Type","text/html");
		response().body().from_file(template);
	}
		
}

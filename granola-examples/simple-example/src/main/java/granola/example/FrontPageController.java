package granola.example;

import granola.mvc.StandardController;

public class FrontPageController extends StandardController
{

	public void index()
	{
		response().body().from_file("templates/frontpage.html");
	}
	
}

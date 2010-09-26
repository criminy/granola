package granola.internal;

import granola.mvc.StandardController;

/**
 * Core controller which is used when a request is made
 * to an invalid controller.
 * 
 * @author criminy
 *
 */
public class CoreController extends StandardController
{
	public void view()
	{
		response().body().from_string("granola-core: core controller");
	}
}
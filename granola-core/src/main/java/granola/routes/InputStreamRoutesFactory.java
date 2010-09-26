package granola.routes;

import static granola.internal.util.iterators.Iterators.line_iter;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InputStreamRoutesFactory implements RouteFactory {

	@Override
	public List<Route> getRoutes(Object internalObject) {
		
		List<Route> routes = new LinkedList<Route>();
		InputStream is = (InputStream) internalObject;



		// List<String> lines = FileUtils.readLines(routesFile, "UTF-8");
		// List<Route> routes = new java.util.ArrayList<Route>();

		
			for (String s : line_iter(is)) {
				if (s.trim().equals(""))
					continue; // blank line
				if (s.trim().matches("^#.*"))
					continue; // comment
				String[] arr = s.split(" ");

				List<String> viewArgs = Arrays.asList(arr).subList(3,
						arr.length);

				Route r = new Route();
				r.url = arr[1];
				r.httpMethod = arr[0];
				r.javaMethod = lookupMethod(arr[2], arr[1].split("\\{").length
						- 1 + viewArgs.size());

				Route.listPairsToMap(viewArgs, r.defaultValues);
				routes.add(r);
			}
		return routes;
	}

	public static Method lookupMethod(String str, int parameterLength) {
		String[] s = str.split("\\.");
		try {
			String className = join(".",
					Arrays.asList(s).subList(0, s.length - 1));
			Class<?> c = Class.forName(className);
			for (Method x : c.getMethods()) {
				if (x.getName().equals(s[s.length - 1])) {
					// if(x.getParameterTypes().length == parameterLength)
					// {
					return x;
					// }
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	public static String join(String delimeter, List<?> l) {
		String s = "";
		for (Object o : l) {
			s += o + delimeter;
		}
		return s.substring(0, s.length() - delimeter.length());
	}

}

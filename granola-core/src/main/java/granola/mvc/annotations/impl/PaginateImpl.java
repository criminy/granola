package granola.mvc.annotations.impl;

import java.lang.reflect.Method;
import java.util.List;

import granola.annotations.meta.ParameterProcessor;
import granola.mvc.Controller;
import granola.mvc.annotations.Paginate;

import granola.functional.Function;
import granola.internal.util.reflection.ReflectionUtilities;

/**
 * 
 * @author criminy
 *
 */
public class PaginateImpl<T> implements ParameterProcessor<Paginate, T>
{

	public T process(Paginate param, Class<?> type, Method method,
			Controller c) {
		String name = param.value();
		Object o = c.parameters().get(name);
		
		int pageNumber = 0;
		if(o != null) pageNumber = Integer.parseInt((String) o);

		Function<List<T> > iterCall = ReflectionUtilities.runtimeCreateInstance(param.list());
		try {
			List<T> iter = iterCall.call(null);
			
			c.response().body().context().put(name + ".prev",pageNumber != 0);
			c.response().body().context().put(name + ".next",pageNumber < iter.size()-1);
			c.response().body().context().put(name + ".prevCount",pageNumber-1);
			c.response().body().context().put(name + ".nextCount",pageNumber+1);
			c.response().body().context().put(name + ".pageNumber",pageNumber);
			
			T ret = iter.get(pageNumber);
			c.response().body().context().put(name,ret);
			return ret;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}

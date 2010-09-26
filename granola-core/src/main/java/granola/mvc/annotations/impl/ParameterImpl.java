package granola.mvc.annotations.impl;

import java.lang.reflect.Method;

import granola.annotations.meta.ParameterProcessor;
import granola.mvc.Controller;
import granola.mvc.annotations.Parameter;

/**
 * Implementation of the Parameter annotation which
 * maps the value of the Parameter annotation 
 * to the HTTP request parameters. 
 * 
 * @author criminy
 *
 */
public class ParameterImpl implements ParameterProcessor<Parameter, String>
{
	@Override
	public String process(Parameter param, Class<?> type, Method method,
			Controller c) {
		String name = param.value();
		Object o = c.parameters().get(name);
		
		if(o == null) return null;
		else return o.toString();
	}
}

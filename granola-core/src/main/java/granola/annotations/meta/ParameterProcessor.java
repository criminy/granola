package granola.annotations.meta;

import granola.mvc.Controller;

import java.lang.reflect.Method;

public interface ParameterProcessor<ANNOTATION_TYPE,RETURN_TYPE> {

	public RETURN_TYPE process(ANNOTATION_TYPE a,Class<?> type, Method method,Controller c);
	
}

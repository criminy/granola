package granola.annotations.meta;

import granola.mvc.Controller;

import java.lang.reflect.Method;

/**
 * Interface for the functions which provide values of
 * user-defined parameters.
 *  
 * @author criminy
 * @see ParameterAnnotation
 * @param <ANNOTATION_TYPE> The annotation that this processor is implementing
 * @param <RETURN_TYPE> The return type of the results of the processing.
 */
public interface ParameterProcessor<ANNOTATION_TYPE,RETURN_TYPE> 
{
	/**
	 * Function for processing the given annotation with the current
	 * java method and HTTP request context.
	 *  
	 * @param a The annotation.
	 * @param type The controller class.
	 * @param method The method on the controller.
	 * @param c The controller instance.
	 * @return The value to use for the annotations annotated parameter.
	 */
	public RETURN_TYPE process(ANNOTATION_TYPE a,Class<?> type, Method method,Controller c);
}

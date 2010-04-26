package granola.mvc.annotations;

import granola.annotations.meta.ParameterAnnotation;
import granola.mvc.annotations.impl.ParameterImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that injects a URL-specified parameter
 * into an argument, as a string.
 * @author criminy
 *
 */
@ParameterAnnotation(value = ParameterImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
	String value();
}

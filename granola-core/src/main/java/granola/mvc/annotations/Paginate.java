package granola.mvc.annotations;

import granola.annotations.meta.ParameterAnnotation;
import granola.functional.Function;
import granola.mvc.annotations.impl.PaginateImpl;
import granola.mvc.annotations.impl.ParameterImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Annotation that uses the parameter for pagination.
 * 
 * @author criminy
 *
 */
@ParameterAnnotation(value = PaginateImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Paginate {
	String value();
	Class list();
}

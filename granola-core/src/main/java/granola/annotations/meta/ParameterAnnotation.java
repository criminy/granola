package granola.annotations.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A meta-annotation applied on 
 * user-defined annotations that 
 * are used for marking parameters 
 * of View functions.
 * 
 * @author criminy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ParameterAnnotation {

	Class value();
}
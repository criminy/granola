package granola.annotations.meta;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterAnnotation {

	Class<? extends ParameterProcessor<?,?>> value();
}
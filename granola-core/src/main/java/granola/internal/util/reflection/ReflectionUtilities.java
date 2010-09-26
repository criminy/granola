package granola.internal.util.reflection;

import granola.exceptions.ExceptionConverter;
import granola.functional.Function;

/**
 * Utilities for runtime class and method lookup. 
 * @author criminy
 *
 */
public class ReflectionUtilities {

	
	/** 
	 * Create an instance of the given class, with
	 * the default constructor and wrap the exceptions 
	 * with a RuntimeException.
	 * @param <T> The type of class to create.
	 * @param classname The fully-qualified name of the class.
	 * @return The instance of the class.
	 * @throws RuntimeException on any errors.
	 */
	public static <T> T runtimeCreateInstance(Class classname)
	{
		try {
			return (T) classname.newInstance();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}			
	}
	
	/** 
	 * Create an instance of the given class, with
	 * the default constructor and wrap the exceptions 
	 * with a RuntimeException.
	 * @param <T> The type of class to create.
	 * @param classname The fully-qualified name of the class.
	 * @return The instance of the class.
	 * @throws RuntimeException on any errors.
	 */
	public static <T> T runtimeCreateInstance(String classname)
	{
		try {
			return (T) Class.forName(classname).newInstance();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}			
	}
	
	/**
	 * Create an instance of the given class, with
	 * the default constructor and send the exceptions
	 * to the given function, to be translated as needed.
	 * @param <T> The instance type.
	 * @param <EXN> The exception type this function will throw.
	 * @param classname The fully-qualified class name.
	 * @param exnCallable The function to call on exception.
	 * @return The instance.
	 * @throws EXN If There is an error creating the class.
	 */
	public static <T, EXN extends Throwable> T runtimeCreateInstance(String classname, Function<EXN> exnCallable) throws EXN
	{
		try {
			return (T) Class.forName(classname).newInstance();
		} catch (Throwable e) {
			try {
				throw exnCallable.call(e);
			} catch (Throwable e1) {
				throw new RuntimeException(e1);
			}
		}		
	}
	
	/**
	 * Create an instance of the given class, with
	 * the default constructor and convert thrown exceptions
	 * to the given exception.
	 * 
	 * @param <T> The type of class to create.
	 * @param <EXN> The exception type this function will throw.
	 * @param classname The fully-qualified class name.
	 * @param exn The exception to throw
	 * @return The instance
	 * @throws EXN If There is an error creating the class.
	 */
	public static <T, EXN extends Throwable> T runtimeCreateInstance(String classname, Class<EXN> exn) throws EXN
	{
		try {
			return (T) Class.forName(classname).newInstance();
		} catch (Throwable e) {
			try {
				throw (new ExceptionConverter<EXN>(exn)).call(e);
			} catch (Throwable e1) {
				throw new RuntimeException(e1);
			}
		}		
	}
}

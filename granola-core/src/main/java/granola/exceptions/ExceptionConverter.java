package granola.exceptions;

import granola.functional.Function;

/**
 * Callable Class which attempts to convert one exception to another exception.
 * @author criminy
 *
 * @param <DEST_EXN>
 */
public class ExceptionConverter<DEST_EXN> implements Function<DEST_EXN>
{
	Class<DEST_EXN> clazz;
	public ExceptionConverter(Class<DEST_EXN> clazz)
	{
		this.clazz = clazz;
	}
	

	public DEST_EXN call(Object o) throws Throwable {
		try {
			return clazz.getConstructor(o.getClass()).newInstance(o);
		}catch(Throwable thr)
		{
			throw new RuntimeException(thr);
		}
	}
}



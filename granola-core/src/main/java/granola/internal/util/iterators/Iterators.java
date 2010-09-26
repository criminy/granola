package granola.internal.util.iterators;

import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

public class Iterators {

	public static <T, E> Iterable<Pair<T,E>> zip_iter(Collection<T> _f,Collection<E> _s)	
	{
		return new ZipIterable<T, E>(_f, _s);		
	}
	
	public static <T,E> Iterable<Pair<T,E>> map_iter(Map<T,E> map)
	{
		return new MapIterable<T, E>(map);
	}
	
	
	public static <E> Iterable<E> enumeration_iter(Class<E> c,@SuppressWarnings("rawtypes") Enumeration et)
	{
		return new EnumerationIterable<E>(et);
	}

	public static <E> Iterable<E> enumeration_iter(Enumeration<?> et)
	{
		return new EnumerationIterable<E>(et);
	}
	

	public static <T> Iterable<String> line_iter(InputStream is)
	{
		return new Readline(is);
	}
}

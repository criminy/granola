package granola.internal.util;

import java.util.Enumeration;
import java.util.Iterator;

class EnumerationIterable<T> implements Iterable<T>
{
	Enumeration<T> enumeration;
	class EnumerationIterator implements Iterator<T>
	{
		Enumeration<T> enumeration;
		
		@Override
		public boolean hasNext() {
			return enumeration.hasMoreElements();
		}
		@Override
		public T next() {
			return enumeration.nextElement();
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	@Override
	public Iterator<T> iterator() {
		EnumerationIterator et = new EnumerationIterator();
		et.enumeration = enumeration;
		return et;
	}
	
	@SuppressWarnings("unchecked")
	public EnumerationIterable(Enumeration et) {
		this.enumeration = et;
	}
}
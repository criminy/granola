package granola.internal.util.iterators;

import java.util.Enumeration;
import java.util.Iterator;

class EnumerationIterable<T> implements Iterable<T>
{
	Enumeration<T> enumeration;
	class EnumerationIterator implements Iterator<T>
	{
		Enumeration<T> enumeration;
		
		public boolean hasNext() {
			return enumeration.hasMoreElements();
		}
		
		public T next() {
			return enumeration.nextElement();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	public Iterator<T> iterator() {
		EnumerationIterator et = new EnumerationIterator();
		et.enumeration = enumeration;
		return et;
	}
		
	@SuppressWarnings("unchecked")
	public EnumerationIterable(@SuppressWarnings("rawtypes") Enumeration et) {
		this.enumeration = et;
	}
}
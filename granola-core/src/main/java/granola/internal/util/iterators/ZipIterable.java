package granola.internal.util.iterators;

import java.util.Collection;
import java.util.Iterator;


class ZipIterable<T, E> implements Iterable<Pair<T,E>>{

	
	Collection<T> first;
	Collection<E> second;
	public ZipIterable(Collection<T> _f,Collection<E> _s) {
		first = _f;
		second = _s;
	}
		
	public Iterator<Pair<T, E>> iterator() {
		return new DoubleListIterator(first,second);
	}
	
	class DoubleListIterator implements Iterator<Pair<T,E>>
	{
		
		public DoubleListIterator(Collection<T> _f,Collection<E> _s) {

			firstIterator = _f.iterator();
			secondIterator = _s.iterator();
			first = _f;
			second = _s;
		}
				
		Collection<T> first;
		Collection<E> second;
		Iterator<T> firstIterator;
		Iterator<E> secondIterator;
		
	
		public boolean hasNext() {
			return firstIterator.hasNext();
		}
	
		public Pair<T, E> next() {
			Pair<T,E> p = new Pair<T, E>();
			p.setKey(firstIterator.next());
			p.setValue(secondIterator.next());
			return p;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}

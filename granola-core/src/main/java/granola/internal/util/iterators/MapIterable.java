package granola.internal.util.iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class  MapIterable<T,E> implements Iterable<Pair<T,E>> {

	class MapIterator implements Iterator<Pair<T,E>>
	{
		
		public MapIterator(Map<T,E> m) {
			this._map = m;
			keys = m.keySet();
			values = m.values();
			keyIterator = keys.iterator();
		}
		
		Map<T,E> _map;
		Set<T> keys;
		Collection<E> values;
		Iterator<T> keyIterator;
		
		public boolean hasNext() {
			return keyIterator.hasNext();
		}

		
		public Pair<T, E> next() {
			Pair<T,E> p = new Pair<T, E>();
			p.setKey(keyIterator.next());
			p.setValue(map.get(p.getKey()));
			return p;
		}

		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	Map<T,E> map;
	
	
	public Iterator<Pair<T, E>> iterator() {
		return new MapIterator(map);
	}
 
	public MapIterable(Map<T,E> map) {
		this.map = map;
	}
	
}

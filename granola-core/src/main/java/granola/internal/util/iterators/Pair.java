package granola.internal.util.iterators;

public class Pair<T,E> {

	private T key;
	private E value;
	
	public void setKey(T key) {
		this.key = key;
	}
	public void setValue(E value) {
		this.value = value;
	}
	public T getKey() {
		return key;
	}
	public E getValue() {
		return value;
	}
	
}

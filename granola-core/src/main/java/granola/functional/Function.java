package granola.functional;

public interface Function<RET> {

	public RET call(Object o) throws Throwable;
	
}

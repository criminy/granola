package granola.servlet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * Proxy which treats a HttpServletRequest.getParameterMap
 * as a standard key-value map.
 * 
 * @author criminy
 *
 */
public class ParameterMapProxy implements Map<Object,Object>{

	private HttpServletRequest req;
	
	public ParameterMapProxy(HttpServletRequest req)
	{
		this.req = req;
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		return req.getParameterMap().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return req.getParameterMap().containsValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<java.util.Map.Entry<Object,Object> > entrySet() {
		return req.getParameterMap().entrySet();
	}


	@Override
	public boolean isEmpty() {
		return req.getParameterMap().isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Object> keySet() {
		return req.getParameterMap().keySet();
	}

	@Override
	public int size() {
		return req.getParameterMap().size();
	}

	@Override
	public Object get(Object key) {
		return req.getParameter((String) key);
	}

	@Override
	public Object put(Object key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(@SuppressWarnings("rawtypes") Map m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection values() {
		return req.getParameterMap().values();
	}
}

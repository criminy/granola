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
		
	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean containsKey(Object key) {
		return req.getParameterMap().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return req.getParameterMap().containsValue(value);
	}

	@SuppressWarnings("unchecked")
	public Set<java.util.Map.Entry<Object,Object> > entrySet() {
		return req.getParameterMap().entrySet();
	}

	
	public boolean isEmpty() {
		return req.getParameterMap().isEmpty();
	}

	@SuppressWarnings("unchecked")
	public Set<Object> keySet() {
		return req.getParameterMap().keySet();
	}

	public int size() {
		return req.getParameterMap().size();
	}

	public Object get(Object key) {
		return req.getParameter((String) key);
	}

	public Object put(Object key, Object value) {
		throw new UnsupportedOperationException();
	}

	public void putAll(@SuppressWarnings("rawtypes") Map m) {
		throw new UnsupportedOperationException();
	}

	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection values() {
		return req.getParameterMap().values();
	}
}

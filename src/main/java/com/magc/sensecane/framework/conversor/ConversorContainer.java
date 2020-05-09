package com.magc.sensecane.framework.conversor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ConversorContainer {

	private final Logger log = Logger.getLogger(ConversorContainer.class.getName());
	private final Map<Class, Conversor> cache;

	public ConversorContainer() {
		this.cache = new ConcurrentHashMap<Class, Conversor>();
	}
	
	public <T> Conversor<T,?> register(Class<T> clazz, Conversor<T,?> conversor) {
		return this.cache.put(clazz, conversor);
	}
	
	public <T> void unregister(Class<T> clazz) {
		this.cache.remove(clazz);
	}
	
	public <T,R> Conversor<T, R> get(Class<T> clazz) {
		return this.cache.get(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <R> R convert(Object object) {
		R result = null;
		
		try {
			Class c = this.cache.keySet().stream().filter(e -> {
				Boolean res;
				try {
					res = this.cache.get(e).canProcess(object);
				} catch (Exception ex) {
					res = false;
				}
				return res;
			}).findFirst().orElse(null);
			if (c != null) {
				Conversor conversor = this.cache.get(c);
				result = (R) conversor.convert(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Set<Class> keySet() {
		return cache.keySet();
	}
}

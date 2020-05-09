package com.magc.sensecane.framework.container;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class DefaultContainer implements Container {

	protected final Map<Class<?>, Object> instances;
	
	public DefaultContainer() {
		this(new HashMap<Class<?>, Object>());
	}
	
	public DefaultContainer(Map<Class<?>, Object> map) {
		this.instances = map;
	}
	
	@Override
	public <T> T register(T entity) {
		Class c = entity.getClass();
		return (T) instances.put(c, entity);
	}
	
	@Override
	public <T> T register(Class<T> clazz, T entity) {
		instances.put(clazz, entity);
		return (T) instances.get(clazz);
	}

	@Override
	public <T> T unregister(Class<T> clazz) {
		return (T) (containsClass(clazz) ? instances.put(clazz, null) : null);
	}

	@Override
	public <T> T unregister(Class<T> clazz, T entity) {
		return (T) (containsClass(clazz) ? instances.remove(clazz) : null);
	}

	@Override
	public Boolean containsClass(Class clazz) {
		return instances.containsKey(clazz);
	}

	@Override
	public <T> T get(final Class<T> clazz) {
		return (T) instances.get(clazz);
	}
	
	public List<Class> filterBySuperClass(Class<?> clazz) {
		return this.instances.values().stream()
			.map (e -> e.getClass())
			.filter(c -> {
				Class temp = c;
				while ( temp != null && temp.getClass() != clazz && temp.getSuperclass() != null ) {
					System.out.println(temp);
					temp = temp.getSuperclass();
				}
				return temp == clazz && temp != c;
			})
			.collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> filterByInterface(Class<?> clazz) {
		return (List<T>) this.instances.values().stream()
			.filter(c -> {
				boolean found = false;
				Class temp = c.getClass();
				outerloop:
				while ( temp.getSuperclass() != null && !found) {
					for (Type interfaz : Arrays.asList(temp.getInterfaces())) {
						if (interfaz.equals(clazz)) {
							found = true;
							break outerloop;
						}
					}
					temp = temp.getSuperclass();
				}
				return found;
			})
			.collect(Collectors.toList());
	}
	
//	public <T> T filterByInterfaceAndType(Class interfaze, Class type) {
//		return this.<T>filterByInterface(interfaze).stream()
//		.filter(e -> ReflectionUtils.usesParameterizedType(e, type) != null)
//		.findFirst()
//		.get();
//	}

}

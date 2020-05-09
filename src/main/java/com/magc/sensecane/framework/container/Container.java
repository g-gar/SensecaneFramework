package com.magc.sensecane.framework.container;

public interface Container {

	/* register an entity of type T in the container and get its class by reflection */
	<T> T register(T entity);
	<T> T register(Class<T> clazz, T entity);
	
	/* removes instance for class */
	<T> T unregister(Class<T> clazz);
	/* removes class key from container */
	<T> T unregister(Class<T> clazz, T entity);
	
	Boolean containsClass(Class clazz);
	
	<T> T get(Class<T> clazz);
	
}

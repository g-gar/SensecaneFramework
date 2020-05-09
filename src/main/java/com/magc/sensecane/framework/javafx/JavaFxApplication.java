package com.magc.sensecane.framework.javafx;

import java.util.logging.Logger;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.container.DefaultContainer;

import javafx.application.Application;
import javafx.application.Platform;

public abstract class JavaFxApplication extends Application implements Container {

	protected final Logger log;
	protected final Container container;
	
	public JavaFxApplication() {
		this(new DefaultContainer() {});
	}
	
	public JavaFxApplication(Container container) {
		this.container = container;
		log = Logger.getAnonymousLogger();
	}
	
	public void execute(Runnable runnable) {
		try {
			if (Platform.isFxApplicationThread()) {
				runnable.run();
			} else {
				Platform.runLater(runnable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public <T> T register(T entity) {
		return container.register(entity);
	}

	@Override
	public <T> T register(Class<T> clazz, T entity) {
		return container.register(clazz, entity);
	}

	@Override
	public <T> T unregister(Class<T> clazz) {
		return container.unregister(clazz);
	}

	@Override
	public <T> T unregister(Class<T> clazz, T entity) {
		return container.unregister(clazz, entity);
	}

	@Override
	public Boolean containsClass(Class clazz) {
		return container.containsClass(clazz);
	}

	@Override
	public <T> T get(Class<T> clazz) {
		return container.get(clazz);
	}
}

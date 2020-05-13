package com.magc.sensecane.framework.conversor;

import com.magc.sensecane.framework.container.Container;

public abstract class AbstractConversor<T, R> implements Conversor<T, R> {

	protected final Container container;
	
	public AbstractConversor(Container container) {
		this.container = container;
	}

	public Boolean canProcess(T param) {
		return false;
	}
}

package com.magc.sensecane.framework.utils;

import com.magc.sensecane.framework.container.Container;

public abstract class AbstractUtil<Type, ReturnType> implements Util<Type, ReturnType> {

	protected final Container container;
	
	public AbstractUtil(Container container) {
		this.container = container;
	}

}

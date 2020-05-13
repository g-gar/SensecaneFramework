package com.magc.sensecane.framework.model.filter;

public interface Filter<A,B> {
	
	Boolean apply(A a, B b);
	
}

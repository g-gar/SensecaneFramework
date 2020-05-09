package com.magc.sensecane.framework.conversor;

public interface Conversor<T,R> {

	R convert(T param);
	Boolean canProcess(T param);
	
}

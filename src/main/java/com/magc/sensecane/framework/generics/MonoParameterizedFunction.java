package com.magc.sensecane.framework.generics;

public interface MonoParameterizedFunction<A,R> extends VariableNumberParameterizedFunction {

	R apply(A param);
	
}

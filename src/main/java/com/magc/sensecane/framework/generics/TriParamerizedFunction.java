package com.magc.sensecane.framework.generics;

public interface TriParamerizedFunction<A,B,C,R> extends VariableNumberParameterizedFunction {

	R apply(A param1, B param2, C param3);
	
}

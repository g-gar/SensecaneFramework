package com.magc.sensecane.framework.generics;

public interface BiParameterizedFunction<A, B, R> extends VariableNumberParameterizedFunction {

	R apply(A param1, B param2);
	
}

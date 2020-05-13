package com.magc.sensecane.framework.facade.util;

import java.util.function.Supplier;

public class AbstractFacadeUtil {

	public <R> R tryOr(Supplier<R>...fns) {
		R result = null;
		
		boolean success = false;
		int i = 0;
		while (!success && i < fns.length) {
			Supplier<R> fn = fns[i++];
			try {
				result = fn.get();
				success = true;
			} catch (Exception e) {
				
			}
		}
		
		if (!success) {
			throw new RuntimeException(new Exception(String.format("[%s] Couldn't find a successful method to apply\"", this.getClass())));
		}
		
		return result;
	}
	
}

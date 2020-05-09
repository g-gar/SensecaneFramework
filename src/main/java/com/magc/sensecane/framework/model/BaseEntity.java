package com.magc.sensecane.framework.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity {

	public BaseEntity() {
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		try {
			builder.append(this.getClass().getSimpleName() + " [");
			Field[] oa;
			for (Field f : (oa = this.getClass().getDeclaredFields())) {
				f.setAccessible(true);
				builder.append(f.getName() + ": " + f.get(this) + (!f.equals(oa[oa.length - 1]) ? ", " : ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return builder.append("]").toString();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		return obj != null && this.getClass() == obj.getClass()
				? this.getAttributes().equals(((BaseEntity) obj).getAttributes())
				: false;
	}

	public Map<String, Object> getAttributes() {
		Map<String, Object> results = null;

		try {
			results = new HashMap<String, Object>();
			for (Field f : this.getClass().getDeclaredFields()) {
				boolean accesible = f.isAccessible();
				f.setAccessible(true);
				results.put(f.getName(), f.get(this));
				f.setAccessible(accesible);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

}

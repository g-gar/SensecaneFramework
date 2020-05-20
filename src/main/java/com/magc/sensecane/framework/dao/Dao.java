package com.magc.sensecane.framework.dao;

import java.util.List;

import com.magc.sensecane.framework.exception.InstanceNotFoundException;
import com.magc.sensecane.framework.model.BaseEntity;

public interface Dao<T extends BaseEntity> {
	Boolean contains(T entity) throws InstanceNotFoundException;
	T find(Integer id) throws InstanceNotFoundException;
	List<T> findAll();
	T insertOrUpdate(T entity);
	T remove(T entity);
	List<T> removeAll();
	void truncate();
	
	T insert(T entity);
	T update(T entity);
}

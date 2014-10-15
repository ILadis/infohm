package de.ladis.infohm.core.dao;

import java.util.List;

public interface Dao<K, E> {

	public E find(K key);

	public List<E> list();

	public void insert(E entity);

	public void update(E entity);

	public void delete(E entity);

}

package de.ladis.infohm.core.dao;

import java.util.List;

public interface Dao<K, E> {

	public E find(K key) throws DaoException;

	public List<E> list() throws DaoException;

	public void insert(E entity) throws DaoException;

	public void update(E entity) throws DaoException;

	public void delete(E entity) throws DaoException;

}

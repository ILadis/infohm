package de.ladis.infohm.core.dao.domain;

import java.util.List;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.domain.Publisher;

public interface PublisherDao extends Dao<Long, Publisher> {

	public List<Publisher> starred() throws DaoException;

	public void star(Publisher entity) throws DaoException;

	public void unstarAll() throws DaoException;

	public void unstar(Publisher entity) throws DaoException;

}

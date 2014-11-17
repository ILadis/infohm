package de.ladis.infohm.core.dao.domain;

import java.util.List;

import com.google.common.collect.Range;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;

public interface EventDao extends Dao<Long, Event> {

	public Event find(Publisher entity, Long key) throws DaoException;

	public List<Event> list(Publisher entity) throws DaoException;

	public List<Event> list(Publisher entity, Range<Integer> range) throws DaoException;

	public Event lastOf(Publisher entity) throws DaoException;

	public List<Event> since(Publisher key, Event entity) throws DaoException;

	public void insert(Publisher key, Event entity) throws DaoException;

}

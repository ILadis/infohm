package de.ladis.infohm.core.dao.domain;

import java.util.List;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.domain.Publisher;

public interface PublisherDao extends Dao<Long, Publisher> {

	public List<Publisher> starred();

	public void star(Publisher entity);

	public void unstarAll();

	public void unstar(Publisher entity);

}

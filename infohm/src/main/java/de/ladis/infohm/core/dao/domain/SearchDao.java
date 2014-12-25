package de.ladis.infohm.core.dao.domain;

import java.util.List;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.domain.Search;

public interface SearchDao extends Dao<Void, Search> {

	public List<Search> search(String query);

}

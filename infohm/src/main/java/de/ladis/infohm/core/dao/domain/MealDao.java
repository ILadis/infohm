package de.ladis.infohm.core.dao.domain;

import java.util.List;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Menu;

public interface MealDao extends Dao<Long, Menu> {

	public List<Menu> list(Cafeteria entity) throws DaoException;

	public List<Menu> currentWeekOf(Cafeteria entity) throws DaoException;

	public List<Menu> nextWeekOf(Cafeteria entity) throws DaoException;

	public void insert(Cafeteria key, Menu entity) throws DaoException;

	public void update(Cafeteria key, Menu entity) throws DaoException;

}

package de.ladis.infohm.core.dao.http.meal;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HttpContext;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.MealDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.dao.http.handler.ParserResponseHandler;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.parser.xml.menu.XmlMenusParser;

public class MealHttpDao extends HttpDao<Long, Menu> implements MealDao {

	private final HttpHost host;
	private final HttpContext context;
	private final HttpRequestFactory factory;

	public MealHttpDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		super(client);

		this.host = host;
		this.context = context;
		this.factory = factory;
	}

	@Override
	public Menu find(Long key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Menu> list() throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Menu> list(Cafeteria entity) {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Menu> currentWeekOf(Cafeteria entity) throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/cafeterians/" + entity.getId() + "/menus/currentweek");
			ResponseHandler<List<Menu>> handler = new ParserResponseHandler<List<Menu>>(new XmlMenusParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public List<Menu> nextWeekOf(Cafeteria entity) throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/cafeterians/" + entity.getId() + "/menus/nextweek");
			ResponseHandler<List<Menu>> handler = new ParserResponseHandler<List<Menu>>(new XmlMenusParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public void insert(Menu entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void insert(Cafeteria key, Menu entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Menu entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Cafeteria key, Menu entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(Menu entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

}

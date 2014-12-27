package de.ladis.infohm.core.dao.http.cafeteria;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HttpContext;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.CafeteriaDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.dao.http.handler.ParserResponseHandler;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.parser.xml.cafeteria.XmlCafeteriasParser;

public class CafeteriaHttpDao extends HttpDao<Long, Cafeteria> implements CafeteriaDao {

	private final HttpHost host;
	private final HttpContext context;
	private final HttpRequestFactory factory;

	public CafeteriaHttpDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		super(client);

		this.host = host;
		this.context = context;
		this.factory = factory;
	}

	@Override
	public Cafeteria find(Long key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Cafeteria> list() throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/cafeterians");
			ResponseHandler<List<Cafeteria>> handler = new ParserResponseHandler<List<Cafeteria>>(new XmlCafeteriasParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public void insert(Cafeteria entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Cafeteria entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(Cafeteria entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

}

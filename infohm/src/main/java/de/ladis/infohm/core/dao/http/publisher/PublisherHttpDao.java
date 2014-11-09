package de.ladis.infohm.core.dao.http.publisher;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HttpContext;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.PublisherDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.dao.http.handler.HttpDaoResponseHandler;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.xml.publisher.XmlPublishersParser;

public class PublisherHttpDao extends HttpDao<Integer, Publisher> implements PublisherDao {

	private final HttpHost host;
	private final HttpContext context;
	private final HttpRequestFactory factory;

	public PublisherHttpDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		super(client);

		this.host = host;
		this.context = context;
		this.factory = factory;
	}

	@Override
	public Publisher find(Integer key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Publisher> list() throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/publishers");
			ResponseHandler<List<Publisher>> handler = new HttpDaoResponseHandler<List<Publisher>>(new XmlPublishersParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public void insert(Publisher entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Publisher entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(Publisher entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

}

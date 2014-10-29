package de.ladis.infohm.core.dao.http.publisher;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.PublisherDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.domain.Publisher;

public class PublisherHttpDao extends HttpDao<Integer, Publisher> implements PublisherDao {

	private final String baseUri;

	public PublisherHttpDao(HttpClient client, String baseUri) {
		super(client);

		this.baseUri = baseUri;
	}

	@Override
	public Publisher find(Integer key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Publisher> list() throws DaoException {
		try {
			return http().execute(new HttpGet(baseUri + "/publishers"), PublishersResponseHandler.getInstance());
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

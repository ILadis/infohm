package de.ladis.infohm.core.dao.http.feedback;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.FeedbackDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.domain.Feedback;

public class FeedbackHttpDao extends HttpDao<Long, Feedback> implements FeedbackDao {

	private final HttpHost host;
	private final HttpContext context;
	private final HttpRequestFactory factory;

	public FeedbackHttpDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		super(client);

		this.host = host;
		this.context = context;
		this.factory = factory;
	}

	@Override
	public Feedback find(Long key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Feedback> list() throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void insert(Feedback entity) throws DaoException {
		try {
			HttpEntityEnclosingRequest request = (HttpEntityEnclosingRequest) factory.newHttpRequest("POST", "/iscore/rest/tearbox");

			List<NameValuePair> body = new ArrayList<NameValuePair>();
			body.add(new BasicNameValuePair("subject", entity.getSubject()));
			body.add(new BasicNameValuePair("body", entity.getMessage()));
			body.add(new BasicNameValuePair("anonym", entity.isAnonymous().toString()));

			request.setEntity(new UrlEncodedFormEntity(body, HTTP.UTF_8));

			http().execute(host, request, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public void update(Feedback entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(Feedback entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

}

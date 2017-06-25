package de.ladis.infohm.core.dao.http.bookmark;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HttpContext;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.BookmarkDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.dao.http.handler.ParserResponseHandler;
import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.parser.xml.bookmark.XmlBookmarksParser;

public class BookmarkHttpDao extends HttpDao<Long, Bookmark> implements BookmarkDao {

	private final HttpHost host;
	private final HttpContext context;
	private final HttpRequestFactory factory;

	public BookmarkHttpDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		super(client);

		this.host = host;
		this.context = context;
		this.factory = factory;
	}

	@Override
	public Bookmark find(Long key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Bookmark> list() throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/bookmarks");
			ResponseHandler<List<Bookmark>> handler = new ParserResponseHandler<List<Bookmark>>(new XmlBookmarksParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public void insert(Bookmark entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Bookmark entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(Bookmark entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

}

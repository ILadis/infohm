package de.ladis.infohm.core.dao.http.event;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HttpContext;

import com.google.common.collect.Range;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.EventDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.dao.http.handler.ParserResponseHandler;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.xml.event.XmlEventParser;
import de.ladis.infohm.core.parser.xml.event.XmlEventsParser;

public class EventHttpDao extends HttpDao<Long, Event> implements EventDao {

	private final HttpHost host;
	private final HttpContext context;
	private final HttpRequestFactory factory;

	public EventHttpDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		super(client);

		this.host = host;
		this.context = context;
		this.factory = factory;
	}

	@Override
	public Event find(Long key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public Event find(Publisher entity, Long id) throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/publishers/" + entity.getId() + "/events/" + id);
			ResponseHandler<Event> handler = new ParserResponseHandler<Event>(new XmlEventParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public List<Event> list() throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Event> list(Publisher entity) throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/publishers/" + entity.getId() + "/events");
			ResponseHandler<List<Event>> handler = new ParserResponseHandler<List<Event>>(new XmlEventsParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public List<Event> list(Publisher entity, Range<Integer> range) throws DaoException {
		try {
			Integer count = range.upperEndpoint() - range.lowerEndpoint() + 1;
			Integer offset = range.lowerEndpoint();

			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/publishers/" + entity.getId() + "/events?events=" + count + "&offset=" + offset);
			ResponseHandler<List<Event>> handler = new ParserResponseHandler<List<Event>>(new XmlEventsParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public Event lastOf(Publisher entity) throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/publishers/" + entity.getId() + "/events?events=1");
			ResponseHandler<List<Event>> handler = new ParserResponseHandler<List<Event>>(new XmlEventsParser());

			List<Event> result = http().execute(host, request, handler, context);

			if (result.size() > 0) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public List<Event> since(Publisher key, Event entity) throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("GET", "/iscore/rest/publishers/" + key.getId() + "/events?since=" + entity.getId());
			ResponseHandler<List<Event>> handler = new ParserResponseHandler<List<Event>>(new XmlEventsParser());

			return http().execute(host, request, handler, context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	@Override
	public void insert(Event entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void insert(Publisher key, Event entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Event entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(Event entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

}

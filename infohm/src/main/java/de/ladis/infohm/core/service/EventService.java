package de.ladis.infohm.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Range;

import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.EventDao;
import de.ladis.infohm.core.dao.domain.SearchDao;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.domain.Search;
import de.ladis.infohm.core.listener.EventListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class EventService {

	private final EventDao cache, remote;
	private final SearchDao search;
	private final ExecutorFactory executor;
	private final CallbackHandler<EventListener> handler;
	private final List<Publisher> updating;

	public EventService(EventDao cache, EventDao remote, SearchDao search, ExecutorFactory executor) {
		this.cache = cache;
		this.remote = remote;
		this.search = search;
		this.executor = executor;
		this.handler = new CallbackHandler<EventListener>(EventListener.class);
		this.updating = new ArrayList<Publisher>();
	}

	public Call<Boolean> isUpdating(final Publisher publisher) {
		return new AbstractCall<Boolean>(executor.forLocal()) {

			@Override
			public Boolean doSync() {
				synchronized (updating) {
					return updating.contains(publisher);
				}
			}

		};
	}

	public Call<List<Event>> updateAll(final Publisher publisher) {
		return new AbstractCall<List<Event>>(executor.forRemote()) {

			@Override
			public List<Event> doSync() {
				synchronized (updating) {
					updating.add(publisher);
				}

				Event last = cache.lastOf(publisher);

				List<Event> events = Collections.emptyList();
				List<Event> updated = new ArrayList<Event>();

				if (last == null) {
					Range<Integer> range = Range.closed(0, 9);

					do {
						events = remote.list(publisher, range);

						if (events != null) {
							updated.addAll(events);

							for (Event event : events) {
								cache.insert(publisher, event);
								search.insert(Search.with(event));
							}

							range = Range.closed(range.upperEndpoint() + 1, range.upperEndpoint() + 10);
						}
					} while (events != null && events.size() > 0);
				} else {
//					events = remote.since(publisher, last);
					events = remote.list(publisher);

					if (events != null) {
						for (Event event : events) {
							cache.insert(publisher, event);
							search.insert(Search.with(event));
						}

						updated.addAll(events);
					}
				}

				synchronized (updating) {
					updating.remove(publisher);
				}

				handler.callback().onUpdated(publisher, updated);

				return updated;
			}

		};
	}

	public Call<List<Event>> getAll(final Publisher publisher, final Range<Integer> range) {
		return new AbstractCall<List<Event>>(executor.forLocal()) {

			@Override
			public List<Event> doSync() {
				List<Event> events = cache.list(publisher, range);

				handler.callback().onGathered(publisher, events);

				return events;
			}

		};
	}

	public Call<List<Event>> getHighlights(final Range<Integer> range) {
		return new AbstractCall<List<Event>>(executor.forLocal()) {

			@Override
			public List<Event> doSync() {
				List<Event> highlights = cache.highlights(range);

				handler.callback().onHighlights(highlights);

				return highlights;
			}

		};
	}

	public void registerListener(EventListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(EventListener listener) {
		handler.unregister(listener);
	}

}

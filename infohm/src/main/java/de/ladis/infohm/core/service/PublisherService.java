package de.ladis.infohm.core.service;

import java.util.List;
import java.util.concurrent.ExecutorService;

import de.ladis.infohm.core.dao.domain.PublisherDao;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.PublisherListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class PublisherService {

	private final PublisherDao cache, remote;
	private final ExecutorService executor;
	private final CallbackHandler<PublisherListener> handler;

	public PublisherService(PublisherDao cache, PublisherDao remote, ExecutorService executor) {
		this.cache = cache;
		this.remote = remote;
		this.executor = executor;
		this.handler = new CallbackHandler<PublisherListener>(PublisherListener.class);
	}

	public Call<List<Publisher>> updateAll() {
		return new AbstractCall<List<Publisher>>(executor) {

			@Override
			public List<Publisher> doSync() {
				List<Publisher> updated = remote.list();

				for (Publisher entity : updated) {
					cache.insert(entity);
				}

				handler.callback().onUpdated(updated);

				return updated;
			}

		};
	}

	public Call<List<Publisher>> getAll() {
		return new AbstractCall<List<Publisher>>(executor) {

			@Override
			public List<Publisher> doSync() {
				return cache.list();
			}

		};
	}

	public void getStarred() {

	}

	public void starTo(Publisher publisher) {

	}

	public void unstarFrom(Publisher publisher) {

	}

	public void subscribeTo(Publisher publisher) {

	}

	public void unsubscribeFrom(Publisher publisher) {

	}

	public void registerListener(PublisherListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(PublisherListener listener) {
		handler.unregister(listener);
	}

}

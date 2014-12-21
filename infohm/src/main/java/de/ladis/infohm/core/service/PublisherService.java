package de.ladis.infohm.core.service;

import java.util.List;

import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.PublisherDao;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.PublisherListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class PublisherService {

	private final PublisherDao cache, remote;
	private final ExecutorFactory executor;
	private final CallbackHandler<PublisherListener> handler;
	private Boolean updating;

	public PublisherService(PublisherDao cache, PublisherDao remote, ExecutorFactory executor) {
		this.cache = cache;
		this.remote = remote;
		this.executor = executor;
		this.handler = new CallbackHandler<PublisherListener>(PublisherListener.class);
		this.updating = false;
	}

	public Call<Boolean> isUpdating() {
		return new AbstractCall<Boolean>(executor.forLocal()) {

			@Override
			public Boolean doSync() {
				return updating;
			}

		};
	}

	public Call<List<Publisher>> updateAll() {
		return new AbstractCall<List<Publisher>>(executor.forRemote()) {

			@Override
			public List<Publisher> doSync() {
				updating = true;

				List<Publisher> updated = remote.list();

				if (updated != null) {
					for (Publisher entity : updated) {
						cache.insert(entity);
					}
				}

				updating = false;

				handler.callback().onUpdated(updated);

				return updated;
			}

		};
	}

	public Call<List<Publisher>> getAll() {
		return new AbstractCall<List<Publisher>>(executor.forLocal()) {

			@Override
			public List<Publisher> doSync() {
				List<Publisher> all = cache.list();

				handler.callback().onGathered(all);

				return all;
			}

		};
	}

	public Call<List<Publisher>> getStarred() {
		return new AbstractCall<List<Publisher>>(executor.forLocal()) {

			@Override
			public List<Publisher> doSync() {
				List<Publisher> starred = cache.starred();

				handler.callback().onStarred(starred);

				return starred;
			}

		};
	}

	public Call<Void> starTo(final Publisher publisher) {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				cache.star(publisher);

				return null;
			}

		};
	}

	public Call<Void> unstarFromAll() {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				cache.unstarAll();

				return null;
			}

		};
	}

	public Call<Void> unstarFrom(final Publisher publisher) {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				cache.unstar(publisher);

				return null;
			}

		};
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

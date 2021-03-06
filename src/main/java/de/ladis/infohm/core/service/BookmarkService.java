package de.ladis.infohm.core.service;

import java.util.List;

import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.BookmarkDao;
import de.ladis.infohm.core.dao.domain.SearchDao;
import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.domain.Search;
import de.ladis.infohm.core.listener.BookmarkListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class BookmarkService {

	private final BookmarkDao cache, remote;
	private final SearchDao search;
	private final ExecutorFactory executor;
	private final CallbackHandler<BookmarkListener> handler;

	public BookmarkService(BookmarkDao cache, BookmarkDao remote, SearchDao search, ExecutorFactory executor) {
		this.cache = cache;
		this.remote = remote;
		this.search = search;
		this.executor = executor;
		this.handler = new CallbackHandler<BookmarkListener>(BookmarkListener.class);
	}

	public Call<List<Bookmark>> updateAll() {
		return new AbstractCall<List<Bookmark>>(executor.forRemote()) {

			@Override
			public List<Bookmark> doSync() {
				List<Bookmark> updated = remote.list();

				if (updated != null) {
					for (Bookmark entity : updated) {
						cache.insert(entity);
						search.insert(Search.with(entity));
					}
				}

				handler.callback().onUpdated(updated);

				return updated;
			}

		};
	}

	public Call<List<Bookmark>> getAll() {
		return new AbstractCall<List<Bookmark>>(executor.forLocal()) {

			@Override
			public List<Bookmark> doSync() {
				List<Bookmark> all = cache.list();

				handler.callback().onGathered(all);

				return all;
			}

		};
	}

	public void registerListener(BookmarkListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(BookmarkListener listener) {
		handler.unregister(listener);
	}

}

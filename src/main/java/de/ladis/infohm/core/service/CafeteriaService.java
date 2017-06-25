package de.ladis.infohm.core.service;

import java.util.List;

import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.CafeteriaDao;
import de.ladis.infohm.core.dao.domain.SearchDao;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Search;
import de.ladis.infohm.core.listener.CafeteriaListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class CafeteriaService {

	private final CafeteriaDao cache, remote;
	private final SearchDao search;
	private final ExecutorFactory executor;
	private final CallbackHandler<CafeteriaListener> handler;

	public CafeteriaService(CafeteriaDao cache, CafeteriaDao remote, SearchDao search, ExecutorFactory executor) {
		this.cache = cache;
		this.remote = remote;
		this.search = search;
		this.executor = executor;
		this.handler = new CallbackHandler<CafeteriaListener>(CafeteriaListener.class);
	}

	public Call<List<Cafeteria>> updateAll() {
		return new AbstractCall<List<Cafeteria>>(executor.forRemote()) {

			@Override
			public List<Cafeteria> doSync() {
				List<Cafeteria> updated = remote.list();

				if (updated != null) {
					for (Cafeteria entity : updated) {
						cache.insert(entity);
						search.insert(Search.with(entity));
					}
				}

				handler.callback().onUpdated(updated);

				return updated;
			}

		};
	}

	public Call<List<Cafeteria>> getAll() {
		return new AbstractCall<List<Cafeteria>>(executor.forLocal()) {

			@Override
			public List<Cafeteria> doSync() {
				List<Cafeteria> all = cache.list();

				handler.callback().onGathered(all);

				return all;
			}

		};
	}

	public void registerListener(CafeteriaListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(CafeteriaListener listener) {
		handler.unregister(listener);
	}

}

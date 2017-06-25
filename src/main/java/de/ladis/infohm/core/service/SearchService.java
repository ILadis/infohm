package de.ladis.infohm.core.service;

import static java.util.Collections.*;

import java.util.Comparator;
import java.util.List;

import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.SearchDao;
import de.ladis.infohm.core.domain.Search;
import de.ladis.infohm.core.listener.SearchListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class SearchService {

	private final SearchDao dao;
	private final ExecutorFactory executor;
	private final CallbackHandler<SearchListener> handler;

	public SearchService(SearchDao dao, ExecutorFactory executor) {
		this.dao = dao;
		this.executor = executor;
		this.handler = new CallbackHandler<SearchListener>(SearchListener.class);
	}

	public Call<List<Search>> searchFor(final String query) {
		return new AbstractCall<List<Search>>(executor.forLocal()) {

			@Override
			public List<Search> doSync() {
				List<Search> results = dao.search(query);

				sort(results, new Comparator<Search>() {

					@Override
					public int compare(Search lhs, Search rhs) {
						return (int) Math.signum(rhs.getPriority() - lhs.getPriority());
					}

				});

				handler.callback().onSearchCompleted(results);

				return results;
			}

		};
	}

	public void registerListener(SearchListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(SearchListener listener) {
		handler.unregister(listener);
	}

}

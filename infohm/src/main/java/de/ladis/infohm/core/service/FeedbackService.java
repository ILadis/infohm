package de.ladis.infohm.core.service;

import java.util.List;

import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.FeedbackDao;
import de.ladis.infohm.core.domain.Feedback;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;

public class FeedbackService {

	private final FeedbackDao cache, remote;
	private final ExecutorFactory executor;

	public FeedbackService(FeedbackDao cache, FeedbackDao remote, ExecutorFactory executor) {
		this.cache = cache;
		this.remote = remote;
		this.executor = executor;
	}

	public Call<Void> submitNew(final Feedback feedback) {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				cache.insert(feedback);

				return null;
			}

		};
	}

	public Call<List<Feedback>> syncAll() {
		return new AbstractCall<List<Feedback>>(executor.forRemote()) {

			@Override
			public List<Feedback> doSync() {
				List<Feedback> all = cache.list();

				for (Feedback feedback : all) {
					cache.delete(feedback);
					remote.insert(feedback);
				}

				return all;
			}

		};
	}

}

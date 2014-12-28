package de.ladis.infohm.core.service;

import java.util.ArrayList;
import java.util.List;

import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.MealDao;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.listener.MealListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class MealService {

	private final MealDao cache, remote;
	private final ExecutorFactory executor;
	private final CallbackHandler<MealListener> handler;
	private final List<Cafeteria> updating;

	public MealService(MealDao cache, MealDao remote, ExecutorFactory executor) {
		this.cache = cache;
		this.remote = remote;
		this.executor = executor;
		this.handler = new CallbackHandler<MealListener>(MealListener.class);
		this.updating = new ArrayList<Cafeteria>();
	}

	public Call<Boolean> isUpdating(final Cafeteria cafeteria) {
		return new AbstractCall<Boolean>(executor.forLocal()) {

			@Override
			public Boolean doSync() {
				synchronized (updating) {
					return updating.contains(cafeteria);
				}
			}

		};
	}

	public Call<List<Menu>> updateAll(final Cafeteria cafeteria) {
		return new AbstractCall<List<Menu>>(executor.forRemote()) {

			@Override
			public List<Menu> doSync() {
				synchronized (updating) {
					updating.add(cafeteria);
				}

				List<Menu> updated = new ArrayList<Menu>();

				List<Menu> currentWeek = remote.currentWeekOf(cafeteria);

				if (currentWeek != null) {
					for (Menu menu : currentWeek) {
						cache.insert(cafeteria, menu);
					}

					updated.addAll(currentWeek);
				}

				List<Menu> nextWeek = remote.nextWeekOf(cafeteria);

				if (nextWeek != null) {
					for (Menu menu : nextWeek) {
						cache.insert(cafeteria, menu);
					}

					updated.addAll(currentWeek);
				}

				synchronized (updating) {
					updating.remove(cafeteria);
				}

				handler.callback().onUpdated(cafeteria, updated);

				return updated;
			}

		};
	}

	public Call<List<Menu>> getForCurrentWeek(final Cafeteria cafeteria) {
		return new AbstractCall<List<Menu>>(executor.forLocal()) {

			@Override
			public List<Menu> doSync() {
				List<Menu> menus = cache.currentWeekOf(cafeteria);

				handler.callback().onCurrentWeek(cafeteria, menus);

				return menus;
			}

		};
	}

	public Call<List<Menu>> getForNextWeek(final Cafeteria cafeteria) {
		return new AbstractCall<List<Menu>>(executor.forLocal()) {

			@Override
			public List<Menu> doSync() {
				List<Menu> menus = cache.nextWeekOf(cafeteria);

				handler.callback().onNextWeek(cafeteria, menus);

				return menus;
			}

		};
	}

	public void registerListener(MealListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(MealListener listener) {
		handler.unregister(listener);
	}

}

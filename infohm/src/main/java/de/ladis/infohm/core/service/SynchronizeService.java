package de.ladis.infohm.core.service;

import static de.ladis.infohm.android.component.content.SyncAdapter.*;

import org.joda.time.DateTime;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.SynchronizeDao;
import de.ladis.infohm.core.listener.SynchronizeListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class SynchronizeService {

	private final Context context;
	private final SynchronizeDao dao;
	private final ExecutorFactory executor;
	private final CallbackHandler<SynchronizeListener> handler;

	public SynchronizeService(Context context, SynchronizeDao dao, ExecutorFactory executor) {
		this.context = context;
		this.dao = dao;
		this.executor = executor;
		this.handler = new CallbackHandler<SynchronizeListener>(SynchronizeListener.class);

		registerReceiver();
	}

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter(SYNC_FINISHED_ACTION);

		context.registerReceiver(receiver, filter);
	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			handler.callback().onSynced();
		}

	};

	public Call<DateTime> lastSync(final Account account) {
		return new AbstractCall<DateTime>(executor.forLocal()) {

			@Override
			public DateTime doSync() {
				DateTime when = dao.find(account);

				handler.callback().onLastSync(account, when);

				return when;
			}

		};
	}

	public Call<Void> setSynced(final Account account, final DateTime when) {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				dao.update(account, when);

				return null;
			}

		};
	}

	public Call<Boolean> isSyncing(final Account account) {
		return new AbstractCall<Boolean>(executor.forLocal()) {

			@Override
			public Boolean doSync() {
				return ContentResolver.isSyncActive(account, "de.ladis.infohm.provider.CacheProvider");
			}

		};
	}

	public Call<Void> autoSync(final Account account, final Boolean enable) {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				ContentResolver.setSyncAutomatically(account, "de.ladis.infohm.provider.CacheProvider", enable);

				return null;
			}

		};
	}

	public Call<Void> requestSync(final Account account) {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				ContentResolver.requestSync(account, "de.ladis.infohm.provider.CacheProvider", Bundle.EMPTY);

				return null;
			}

		};
	}

	public void registerListener(SynchronizeListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(SynchronizeListener listener) {
		handler.unregister(listener);
	}

}

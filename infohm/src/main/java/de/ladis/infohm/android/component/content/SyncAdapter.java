package de.ladis.infohm.android.component.content;

import java.util.List;

import javax.inject.Inject;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import de.ladis.infohm.android.component.BaseSyncAdapter;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.EventService;
import de.ladis.infohm.core.service.PublisherService;

public class SyncAdapter extends BaseSyncAdapter {

	public static class Service extends android.app.Service {

		private SyncAdapter adapter;

		@Override
		public void onCreate() {
			adapter = new SyncAdapter(this, true);
		}

		@Override
		public IBinder onBind(Intent intent) {
			return adapter.getSyncAdapterBinder();
		}

	}

	@Inject
	protected AuthenticationService authService;

	@Inject
	protected PublisherService pubService;

	@Inject
	protected EventService eventService;

	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
	}

	public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
		if (!authService.signIn(account).doSync()) {
			syncResult.stats.numAuthExceptions++;
		} else {
			List<Publisher> publishers = pubService.updateAll().doSync();

			if (publishers == null) {
				syncResult.stats.numParseExceptions++;
			} else {
				syncResult.stats.numUpdates += publishers.size();

				for (Publisher publisher : publishers) {
					List<Event> events = eventService.updateAll(publisher).doSync();

					if (events == null) {
						syncResult.stats.numParseExceptions++;
					} else {
						syncResult.stats.numUpdates = events.size();
					}
				}
			}
		}
	}

}

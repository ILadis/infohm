package de.ladis.infohm.android.component.content;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import de.ladis.infohm.android.component.BaseSyncAdapter;
import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Feedback;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.BookmarkService;
import de.ladis.infohm.core.service.EventService;
import de.ladis.infohm.core.service.FeedbackService;
import de.ladis.infohm.core.service.PublisherService;
import de.ladis.infohm.core.service.SynchronizeService;

public class SyncAdapter extends BaseSyncAdapter {

	public static final String SYNC_FINISHED_ACTION = "de.ladis.infohm.android.component.content.SYNC_FINISHED_ACTION";

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
	protected PublisherService publisherService;

	@Inject
	protected EventService eventService;

	@Inject
	protected BookmarkService bookmarkService;

	@Inject
	protected FeedbackService feedbackService;

	@Inject
	protected SynchronizeService syncService;

	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
	}

	public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
		try {
			if (!authService.signIn(account).doSync()) {
				syncResult.stats.numAuthExceptions++;
			} else {
				performPublisherSync(syncResult);
				performBoockmarkSync(syncResult);
				performFeedbackSync(syncResult);
			}
		} catch (Exception e) {
			syncResult.stats.numIoExceptions++;
		}

		finishSync(account);
	}

	private void performPublisherSync(SyncResult syncResult) {
		List<Publisher> publishers = publisherService.updateAll().doSync();

		if (writeToSyncStats(syncResult, publishers)) {
			for (Publisher publisher : publishers) {
				List<Event> events = eventService.updateAll(publisher).doSync();

				writeToSyncStats(syncResult, events);
			}
		}
	}

	private void performBoockmarkSync(SyncResult syncResult) {
		List<Bookmark> bookmarks = bookmarkService.updateAll().doSync();

		writeToSyncStats(syncResult, bookmarks);
	}

	private void performFeedbackSync(SyncResult syncResult) {
		List<Feedback> feedbacks = feedbackService.syncAll().doSync();

		writeToSyncStats(syncResult, feedbacks);
	}

	private boolean writeToSyncStats(SyncResult sync, List<?> result) {
		if (result == null) {
			sync.stats.numParseExceptions++;

			return false;
		} else {
			sync.stats.numUpdates += result.size();

			return true;
		}
	}

	private void finishSync(Account account) {
		syncService.setSynced(account, DateTime.now()).doSync();

		sendBroadcast(new Intent(SYNC_FINISHED_ACTION));
	}

}

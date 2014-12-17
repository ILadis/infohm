package de.ladis.infohm.android.component;

import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;
import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.Intent;

public abstract class BaseSyncAdapter extends AbstractThreadedSyncAdapter implements Injector {

	public BaseSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);

		inject(this);
	}

	public BaseSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);

		inject(this);
	}

	public void sendBroadcast(Intent intent) {
		getContext().sendBroadcast(intent);
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getContext().getApplicationContext();
		app.inject(object);
	}

}

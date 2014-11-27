package de.ladis.infohm.android.component;

import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;
import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;

public abstract class BaseSyncAdapter extends AbstractThreadedSyncAdapter implements Injector {

	private final Context context;

	public BaseSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		this.context = context;

		inject(this);
	}

	public BaseSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
		this.context = context;

		inject(this);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getContext().getApplicationContext();
		app.inject(object);
	}

}

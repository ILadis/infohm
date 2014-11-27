package de.ladis.infohm.android.component;

import android.content.ContentProvider;
import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;

public abstract class BaseContentProvider extends ContentProvider implements Injector {

	@Override
	public boolean onCreate() {
		inject(this);
		return false;
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getContext().getApplicationContext();
		app.inject(object);
	}

}

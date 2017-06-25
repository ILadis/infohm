package de.ladis.infohm.android.component;

import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;
import android.accounts.AbstractAccountAuthenticator;
import android.content.Context;

public abstract class BaseAccountAuthenticator extends AbstractAccountAuthenticator implements Injector {

	private final Context context;

	public BaseAccountAuthenticator(Context context) {
		super(context);
		this.context = context;

		inject(this);
	}

	public Context getContext() {
		return context;
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getContext().getApplicationContext();
		app.inject(object);
	}

}

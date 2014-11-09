package de.ladis.infohm.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import butterknife.ButterKnife;
import de.ladis.infohm.R;
import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;

public abstract class BaseActivity extends ActionBarActivity implements Injector {

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		inject(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView(int layoutResId) {
		super.setContentView(layoutResId);
		setContentView();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		setContentView();
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		setContentView();
	}

	private final void setContentView() {
		// find and set toolbar
		toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		// inject views
		ButterKnife.inject(this);
	}

	public final Toolbar getSupportToolbar() {
		return toolbar;
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getApplication();
		app.inject(object);
	}

}

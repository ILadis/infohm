package de.ladis.infohm.android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import butterknife.ButterKnife;
import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;

public class BaseActivity extends FragmentActivity implements Injector {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		inject(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView(int layoutResId) {
		super.setContentView(layoutResId);
		ButterKnife.inject(this);
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		ButterKnife.inject(this);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		ButterKnife.inject(this);
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getApplication();
		app.inject(object);
	}

}

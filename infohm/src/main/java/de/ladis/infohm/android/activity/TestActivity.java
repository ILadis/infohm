package de.ladis.infohm.android.activity;

import javax.inject.Inject;

import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Toast;

public class TestActivity extends BaseActivity {

	@Inject
	protected NotificationManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (manager != null) {
			Toast.makeText(this, "Did it!", Toast.LENGTH_SHORT).show();
		}
	}

}

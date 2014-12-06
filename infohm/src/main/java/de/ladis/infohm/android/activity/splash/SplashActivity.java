package de.ladis.infohm.android.activity.splash;

import java.util.List;

import javax.inject.Inject;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.activity.account.CreateAccountActivity;
import de.ladis.infohm.android.activity.events.MainActivity;
import de.ladis.infohm.android.activity.welcome.WelcomeActivity;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.PublisherListener;
import de.ladis.infohm.core.listener.SimplePublisherListener;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.PublisherService;

public class SplashActivity extends BaseActivity {

	@Inject
	protected AuthenticationService authService;

	@Inject
	protected PublisherService pubService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		if (savedInstanceState == null) {
			Account account = authService.getAccount().doSync();

			if (account == null) {
				launchCreateAccountActivity();
			} else {
				authService.authenticate(account).doSync();
				pubService.getStarred().doAsync();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		pubService.registerListener(pubListener);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			launchWelcomeActivity();
		}

		finish();
	}

	private final PublisherListener pubListener = new SimplePublisherListener() {

		@Override
		public void onStarred(List<Publisher> publishers) {
			if (publishers.size() <= 0) {
				launchWelcomeActivity();
			} else {
				launchMainActivity();
			}

			finish();
		}

	};

	private void launchWelcomeActivity() {
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}

	private void launchCreateAccountActivity() {
		Intent intent = new Intent(this, CreateAccountActivity.class);
		startActivityForResult(intent, 1);
	}

	private void launchMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();

		pubService.unregisterListener(pubListener);
	}

}

package de.ladis.infohm.android.activity.splash;

import javax.inject.Inject;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.activity.account.CreateAccountActivity;
import de.ladis.infohm.android.activity.main.MainActivity;
import de.ladis.infohm.core.service.AuthenticationService;

public class SplashActivity extends BaseActivity {

	@Inject
	protected AuthenticationService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		if (savedInstanceState == null) {
			handler.sendMessageDelayed(Message.obtain(), 2000);
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Account account = service.getAccount().doSync();

			if (account == null) {
				launchCreateAccountActivity();
			} else {
				launchMainActivity();
				finish();
			}
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			launchMainActivity();
		}

		finish();
	}

	private void launchCreateAccountActivity() {
		Intent intent = new Intent(this, CreateAccountActivity.class);
		startActivityForResult(intent, 1);
	}

	private void launchMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}

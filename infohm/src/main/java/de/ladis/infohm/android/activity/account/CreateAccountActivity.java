package de.ladis.infohm.android.activity.account;

import javax.inject.Inject;

import android.os.Bundle;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.controller.AuthenticationController;
import de.ladis.infohm.core.listener.AuthenticationListener;
import de.ladis.infohm.core.listener.SimpleAuthenticationListener;
import de.ladis.infohm.core.service.AuthenticationService;

public class CreateAccountActivity extends BaseActivity implements AuthenticationController {

	@Inject
	protected AuthenticationService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		setResult(RESULT_CANCELED);
	}

	@Override
	protected void onResume() {
		super.onResume();

		service.registerListener(listener);
	}

	@Override
	public void signIn(String username, String password) {
		service.signIn(username, password).doAsync();
	}

	private final AuthenticationListener listener = new SimpleAuthenticationListener() {

		@Override
		public void onSignedIn(String username, String password) {
			service.addAccount(username, password).doSync();

			setResult(RESULT_OK);
			finish();
		}

		@Override
		public void onSigninFailed() {
			setResult(RESULT_CANCELED);
		}

	};

	@Override
	protected void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

}

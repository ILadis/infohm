package de.ladis.infohm.android.activity.account;

import javax.inject.Inject;

import android.accounts.Account;
import android.os.Bundle;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.controller.AuthenticationController;
import de.ladis.infohm.core.listener.AuthenticationListener;
import de.ladis.infohm.core.listener.SimpleAuthenticationListener;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.SynchronizeService;

public class CreateAccountActivity extends BaseActivity implements AuthenticationController {

	@Inject
	protected AuthenticationService authService;

	@Inject
	protected SynchronizeService syncService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		setResult(RESULT_CANCELED);
	}

	@Override
	protected void onResume() {
		super.onResume();

		authService.registerListener(listener);
	}

	@Override
	public void signIn(String username, String password) {
		authService.signIn(username, password).doAsync();
	}

	private final AuthenticationListener listener = new SimpleAuthenticationListener() {

		@Override
		public void onSignedIn(String username, String password) {
			authService.addAccount(username, password).doSync();

			Account account = authService.getAccount().doSync();
			syncService.autoSync(account, true);

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

		authService.unregisterListener(listener);
	}

}

package de.ladis.infohm.android.activity.authentication;

import javax.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.activity.welcome.WelcomeActivity;
import de.ladis.infohm.android.controller.AuthenticationController;
import de.ladis.infohm.core.listener.AuthenticationListener;
import de.ladis.infohm.core.service.AuthenticationService;

public class AuthenticationActivity extends BaseActivity implements AuthenticationController, AuthenticationListener {

	@Inject
	protected AuthenticationService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);
	}

	@Override
	protected void onResume() {
		super.onResume();

		service.registerListener(this);
	}

	@Override
	public void signin(String username, String password) {
		service.signin(username, password).doAsync();
	}

	@Override
	public void onSignedIn() {
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}

	@Override
	public void onSigninFailed() {
	}

	@Override
	public void onSignedOut() {
	}

	@Override
	protected void onPause() {
		super.onPause();

		service.unregisterListener(this);
	}

}

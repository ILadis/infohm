package de.ladis.infohm.android.activity.authentication;

import javax.inject.Inject;

import android.os.Bundle;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.controller.AuthenticationController;
import de.ladis.infohm.core.service.AuthenticationService;

public class AuthenticationActivity extends BaseActivity implements AuthenticationController {

	@Inject
	protected AuthenticationService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);
	}

	@Override
	public void signin(String username, String password) {
		service.signin(username, password).doAsync();
	}

}

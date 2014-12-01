package de.ladis.infohm.android.fragment.authentication;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import de.ladis.infohm.R;
import de.ladis.infohm.android.controller.AuthenticationController;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.validation.PasswordValidator;
import de.ladis.infohm.android.validation.ViewValidator;
import de.ladis.infohm.android.validation.UsernameValidator;
import de.ladis.infohm.android.widget.EditText;
import de.ladis.infohm.core.listener.AuthenticationListener;
import de.ladis.infohm.core.listener.SimpleAuthenticationListener;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.validation.Validator;

public class AuthenticationFragment extends BaseFragment {

	private AuthenticationController controller;

	@Inject
	protected AuthenticationService service;

	@InjectView(R.id.fragment_authentication_username)
	protected EditText usernameView;

	private Validator<TextView> usernameValidator;

	@InjectView(R.id.fragment_authentication_password)
	protected EditText passwordView;

	private Validator<TextView> passwordValidator;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		controller = (AuthenticationController) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_authentication, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		usernameView.setErrorView(view.findViewById(R.id.fragment_authentication_username_error));
		passwordView.setErrorView(view.findViewById(R.id.fragment_authentication_password_error));

		usernameValidator = new ViewValidator(new UsernameValidator());
		passwordValidator = new ViewValidator(new PasswordValidator());

		if (savedInstanceState != null) {
			usernameView.setText(savedInstanceState.getString("username"));
			usernameView.setEnabled(savedInstanceState.getBoolean("enabled"));
			passwordView.setText(savedInstanceState.getString("password"));
			passwordView.setEnabled(savedInstanceState.getBoolean("enabled"));
			if (savedInstanceState.getBoolean("errors")) {
				validateForm();
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(listener);
	}

	private boolean validateForm() {
		boolean valid = true;

		usernameView.setError(null);
		if (!usernameValidator.valid(usernameView)) {
			usernameView.setError(getString(R.string.fragment_authentication_username_invalid));
			valid = false;
		}

		passwordView.setError(null);
		if (!passwordValidator.valid(passwordView)) {
			passwordView.setError(getString(R.string.fragment_authentication_password_invalid));
			valid = false;
		}

		return valid;
	}

	@OnClick(R.id.fragment_authentication_submit)
	protected void submitCredentials() {
		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();

		if (validateForm()) {
			usernameView.setEnabled(false);
			passwordView.setEnabled(false);

			controller.signIn(username, password);
		}
	}

	private final AuthenticationListener listener = new SimpleAuthenticationListener() {

		@Override
		public void onSignedIn(String username, String password) {
			usernameView.setEnabled(true);
			passwordView.setEnabled(true);
		}

		@Override
		public void onSigninFailed() {
			usernameView.setEnabled(true);
			passwordView.setEnabled(true);
		}

	};

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString("username", usernameView.getText().toString());
		outState.putString("password", passwordView.getText().toString());
		outState.putBoolean("enabled", usernameView.isEnabled());
		outState.putBoolean("errors", usernameView.getError() != null || passwordView.getError() != null);
	}

}

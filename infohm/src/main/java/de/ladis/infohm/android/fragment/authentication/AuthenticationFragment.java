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
import de.ladis.infohm.core.service.AuthenticationService;

public class AuthenticationFragment extends BaseFragment {

	private AuthenticationController controller;

	@Inject
	protected AuthenticationService service;

	@InjectView(R.id.fragment_authentication_username)
	protected TextView usernameView;

	@InjectView(R.id.fragment_authentication_password)
	protected TextView passwordView;

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

		if (savedInstanceState != null) {
			usernameView.setText(savedInstanceState.getString("username"));
			passwordView.setText(savedInstanceState.getString("password"));
		}
	}

	@OnClick(R.id.fragment_authentication_submit)
	protected void submitCredentials() {
		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();

		controller.signIn(username, password);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString("username", usernameView.getText().toString());
		outState.putString("password", passwordView.getText().toString());
	}

}

package de.ladis.infohm.android.fragment.authentication;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.controller.AuthenticationController;
import de.ladis.infohm.android.fragment.BaseFragment;

public class AuthenticationFragment extends BaseFragment {

	private AuthenticationController controller;

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

	@OnClick(R.id.fragment_authentication_submit)
	protected void submitCredentials() {
		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();

		controller.signin(username, password);
	}

}

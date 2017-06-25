package de.ladis.infohm.core.listener;

public abstract class SimpleAuthenticationListener implements AuthenticationListener {

	@Override
	public void onSignedIn(String username, String password) {
	}

	@Override
	public void onSigninFailed() {
	}

	@Override
	public void onSignedOut() {
	}

}

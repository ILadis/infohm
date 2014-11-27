package de.ladis.infohm.core.listener;

public interface AuthenticationListener {

	public void onSignedIn(String username, String password);

	public void onSigninFailed();

	public void onSignedOut();

}

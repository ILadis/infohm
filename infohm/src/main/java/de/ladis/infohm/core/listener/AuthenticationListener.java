package de.ladis.infohm.core.listener;

public interface AuthenticationListener {

	public void onSignedIn();

	public void onSigninFailed();

	public void onSignedOut();

}

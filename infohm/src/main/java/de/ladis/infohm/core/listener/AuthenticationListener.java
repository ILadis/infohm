package de.ladis.infohm.core.listener;

public interface AuthenticationListener {

	public void onSignedIn();

	public boolean onSigninFailed();

	public void onSignedOut();

}

package de.ladis.infohm.core.service;

import java.util.concurrent.ExecutorService;

import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;

import de.ladis.infohm.core.dao.domain.AuthenticationDao;
import de.ladis.infohm.core.listener.AuthenticationListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class AuthenticationService {

	private final AuthenticationDao dao;
	private final ExecutorService executor;
	private final CallbackHandler<AuthenticationListener> handler;

	public AuthenticationService(AuthenticationDao dao, ExecutorService executor) {
		this.dao = dao;
		this.executor = executor;
		this.handler = new CallbackHandler<AuthenticationListener>(AuthenticationListener.class);
	}

	public Call<Boolean> signin(final String username, final String password) {
		return new AbstractCall<Boolean>(executor) {

			@Override
			public Boolean doSync() {
				Credentials credentials = new UsernamePasswordCredentials(username, password);

				Boolean result = dao.signin(credentials);

				if (result) {
					handler.callback().onSignedIn();
				} else {
					handler.callback().onSigninFailed();
				}

				return result;
			}

		};
	}

	public Call<Void> signout() {
		return new AbstractCall<Void>(executor) {

			@Override
			public Void doSync() {
				dao.signout();

				handler.callback().onSignedOut();

				return null;
			}

		};
	}

	public void registerListener(AuthenticationListener listener) {
		handler.register(listener);
	}

	public void unregisterListener(AuthenticationListener listener) {
		handler.unregister(listener);
	}

}

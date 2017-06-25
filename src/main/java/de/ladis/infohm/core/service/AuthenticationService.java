package de.ladis.infohm.core.service;

import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;

import android.accounts.Account;
import android.accounts.AccountManager;
import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.domain.AuthenticationDao;
import de.ladis.infohm.core.listener.AuthenticationListener;
import de.ladis.infohm.util.AbstractCall;
import de.ladis.infohm.util.Call;
import de.ladis.infohm.util.CallbackHandler;

public class AuthenticationService {

	private final AccountManager manager;
	private final AuthenticationDao dao;
	private final ExecutorFactory executor;
	private final CallbackHandler<AuthenticationListener> handler;

	public AuthenticationService(AccountManager manager, AuthenticationDao dao, ExecutorFactory executor) {
		this.manager = manager;
		this.dao = dao;
		this.executor = executor;
		this.handler = new CallbackHandler<AuthenticationListener>(AuthenticationListener.class);
	}

	public Call<Account> getAccount() {
		return new AbstractCall<Account>(executor.forLocal()) {

			@Override
			public Account doSync() {
				Account[] accounts = manager.getAccountsByType("de.infohm");

				if (accounts != null && accounts.length > 0) {
					return accounts[0];
				} else {
					return null;
				}
			}

		};
	}

	public Call<Boolean> addAccount(final String username, final String password) {
		return new AbstractCall<Boolean>(executor.forLocal()) {

			@Override
			public Boolean doSync() {
				Account account = new Account(username, "de.infohm");

				return manager.addAccountExplicitly(account, password, null);
			}

		};
	}

	public Call<Boolean> signIn(final Account account) {
		String username = account.name;
		String password = manager.getPassword(account);

		return signIn(username, password);
	}

	public Call<Boolean> signIn(final String username, final String password) {
		return new AbstractCall<Boolean>(executor.forRemote()) {

			@Override
			public Boolean doSync() {
				Credentials credentials = new UsernamePasswordCredentials(username, password);

				Boolean result = dao.signIn(credentials);

				if (result) {
					handler.callback().onSignedIn(username, password);
				} else {
					handler.callback().onSigninFailed();
				}

				return result;
			}

		};
	}

	public Call<Void> authenticate(Account account) {
		String username = account.name;
		String password = manager.getPassword(account);

		return authenticate(username, password);
	}

	public Call<Void> authenticate(final String username, final String password) {
		return new AbstractCall<Void>(executor.forLocal()) {

			@Override
			public Void doSync() {
				Credentials credentials = new UsernamePasswordCredentials(username, password);

				dao.authenticate(credentials);

				return null;
			}

		};
	}

	public Call<Void> signOut() {
		return new AbstractCall<Void>(executor.forRemote()) {

			@Override
			public Void doSync() {
				dao.signOut();

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

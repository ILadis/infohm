package de.ladis.infohm.android.component.account;

import static android.accounts.AccountManager.*;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import de.ladis.infohm.android.activity.account.CreateAccountActivity;
import de.ladis.infohm.android.component.BaseAccountAuthenticator;

public class AccountAuthenticator extends BaseAccountAuthenticator {

	public static class Service extends android.app.Service {

		private AccountAuthenticator authenticator;

		@Override
		public void onCreate() {
			authenticator = new AccountAuthenticator(this);
		}

		@Override
		public IBinder onBind(Intent intent) {
			return authenticator.getIBinder();
		}

	}

	public AccountAuthenticator(Context context) {
		super(context);
	}

	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
		Intent intent = new Intent(getContext(), CreateAccountActivity.class);

		Bundle bundle = new Bundle();
		bundle.putParcelable(KEY_INTENT, intent);

		return bundle;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account,
			String[] features) throws NetworkErrorException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account,
			String authTokenType, Bundle options) throws NetworkErrorException {
		throw new UnsupportedOperationException();
	}

}

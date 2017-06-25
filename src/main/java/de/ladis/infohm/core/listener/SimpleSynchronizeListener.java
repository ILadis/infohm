package de.ladis.infohm.core.listener;

import org.joda.time.DateTime;

import android.accounts.Account;

public abstract class SimpleSynchronizeListener implements SynchronizeListener {

	@Override
	public void onLastSync(Account account, DateTime when) {
	}

	@Override
	public void onSynced() {
	}

}

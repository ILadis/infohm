package de.ladis.infohm.core.listener;

import org.joda.time.DateTime;

import android.accounts.Account;

public interface SynchronizeListener {

	public void onLastSync(Account account, DateTime when);

	public void onSynced();

}

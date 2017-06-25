package de.ladis.infohm.core.dao.domain;

import org.joda.time.DateTime;

import android.accounts.Account;
import de.ladis.infohm.core.dao.Dao;

public interface SynchronizeDao  extends Dao<Account, DateTime> {

	public void update(Account account, DateTime when);

}

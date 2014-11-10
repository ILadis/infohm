package de.ladis.infohm.core.dao.domain;

import org.apache.http.auth.Credentials;

import de.ladis.infohm.core.dao.Dao;

public interface AuthenticationDao extends Dao<Credentials, Void> {

	public boolean signin(Credentials credentials);

	public void signout();

}

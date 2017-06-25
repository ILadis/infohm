package de.ladis.infohm.core.dao.domain;

import org.apache.http.auth.Credentials;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoException;

public interface AuthenticationDao extends Dao<Credentials, Void> {

	public boolean signIn(Credentials credentials) throws DaoException;

	public void authenticate(Credentials credentials) throws DaoException;

	public void signOut() throws DaoException;

}

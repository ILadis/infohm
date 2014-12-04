package de.ladis.infohm.core.dao.http.authentication;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.http.MockedHttpClient;
import de.ladis.infohm.test.BaseTest;

public class AuthenticationHttpDaoTest extends BaseTest {

	@Override
	protected List<Object> getModules() {
		return Arrays.<Object>asList(
				new AuthenticationHttpDaoTestModule()
		);
	}

	@Inject
	protected MockedHttpClient client;

	@Inject
	protected HttpContext context;

	@Inject
	protected AuthenticationHttpDao dao;

	@Test
	public void httpDaoShouldSignInSuccessfulOnValidHttpResponse() {
		client.setResponseStatusCode(200);

		Credentials credentials = new UsernamePasswordCredentials("yet another user", "yet another password");

		boolean result = dao.signIn(credentials);

		assertThat(result, is(true));

		CredentialsProvider provider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);

		assertThat(provider, notNullValue());
		assertThat(credentials, equalTo(provider.getCredentials(AuthScope.ANY)));
	}

	@Test
	public void httpDaoShouldFailToSignInOnInvalidHttpResponse() {
		client.setResponseStatusCode(500);

		Credentials credentials = new UsernamePasswordCredentials("yet another user", "yet another password");

		boolean result = dao.signIn(credentials);

		assertThat(result, is(false));

		CredentialsProvider provider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);

		assertThat(provider, nullValue());
	}

	@Test
	public void httpDaoShouldSignOutSuccessfullIfPreviouslySignedIn() {
		httpDaoShouldSignInSuccessfulOnValidHttpResponse();

		dao.signOut();

		CredentialsProvider provider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);

		assertThat(provider, nullValue());
	}

	@Test
	public void httpDaoShouldNotSupportTheseMethodsAndThrowExpectedException() {
		try {
			dao.find(null);

			fail("PublisherHttpDao.find() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.insert(null);

			fail("AuthenticationHttpDao.insert() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.update(null);

			fail("AuthenticationHttpDao.update() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.delete(null);

			fail("AuthenticationHttpDao.delete() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}
	}

}

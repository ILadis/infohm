package de.ladis.infohm.core.dao.http.authentication;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.domain.AuthenticationDao;
import de.ladis.infohm.core.dao.http.HttpDao;
import de.ladis.infohm.core.dao.http.handler.AuthenticationResponseHandler;

public class AuthenticationHttpDao extends HttpDao<Credentials, Void> implements AuthenticationDao {

	private final HttpHost host;
	private final HttpContext context;
	private final HttpRequestFactory factory;

	public AuthenticationHttpDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		super(client);

		this.host = host;
		this.context = context;
		this.factory = factory;
	}

	@Override
	public Void find(Credentials key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Void> list() throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void insert(Void entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Void entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(Void entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public boolean signin(Credentials credentials) throws DaoException {
		try {
			HttpEntityEnclosingRequest request = (HttpEntityEnclosingRequest) factory.newHttpRequest("POST", "/rest/auth/login");
			ResponseHandler<Boolean> handler = new AuthenticationResponseHandler();

			addCredentialsToContext(context, credentials);
			addCredentialsToRequest(request, credentials);

			boolean result = http().execute(host, request, handler, context);

			if (!result) {
				removeCredentialsFromContext(context);
			}

			return result;
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	private void addCredentialsToContext(HttpContext context, Credentials credentials) {
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, credentials);

		context.setAttribute(ClientContext.CREDS_PROVIDER, provider);;
		context.setAttribute(ClientPNames.HANDLE_AUTHENTICATION, true);
	}

	private void addCredentialsToRequest(HttpEntityEnclosingRequest request, Credentials credentials)
			throws UnsupportedEncodingException {

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("login", credentials.getUserPrincipal().getName()));
		parameters.add(new BasicNameValuePair("password", credentials.getPassword()));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
		request.setEntity(entity);
	}

	@Override
	public void signout() throws DaoException {
		try {
			HttpRequest request = factory.newHttpRequest("POST", "/rest/auth/logout");

			http().execute(host, request, context);

			removeCredentialsFromContext(context);
		} catch (Exception e) {
			throw new DaoException(this, e);
		}
	}

	private void removeCredentialsFromContext(HttpContext context) {
		context.removeAttribute(ClientContext.CREDS_PROVIDER);
		context.removeAttribute(ClientPNames.HANDLE_AUTHENTICATION);
	}

}

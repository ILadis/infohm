package de.ladis.infohm.core.dao.http.handler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class AuthenticationResponseHandler implements ResponseHandler<Boolean> {

	@Override
	public Boolean handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		return response.getStatusLine().getStatusCode() == 200;
	}

}

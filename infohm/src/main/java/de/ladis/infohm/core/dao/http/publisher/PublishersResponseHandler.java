package de.ladis.infohm.core.dao.http.publisher;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import de.ladis.infohm.core.domain.Publisher;

public class PublishersResponseHandler implements ResponseHandler<List<Publisher>> {

	private static final PublishersResponseHandler INSTANCE = new PublishersResponseHandler();

	public static PublishersResponseHandler getInstance() {
		return INSTANCE;
	}

	private PublishersResponseHandler() {
	}

	@Override
	public List<Publisher> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {

		return Collections.emptyList();
	}

}

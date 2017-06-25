package de.ladis.infohm.core.parser.json.publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.domain.PublishersParser;
import de.ladis.infohm.core.parser.json.JsonParser;

public class JsonPublishersParser extends JsonParser<List<Publisher>> implements PublishersParser {

	@Override
	public List<Publisher> parse(JsonReader reader) throws IOException {
		List<Publisher> publishers = new ArrayList<Publisher>();

		reader.beginArray();
		while (reader.hasNext()) {
			Publisher publisher = parsePublisher(reader);
			publishers.add(publisher);
		}
		reader.endArray();

		return publishers;
	}

	private Publisher parsePublisher(JsonReader reader) {
		// TODO: Implement parsing of Publisher
		return new Publisher();
	}

}

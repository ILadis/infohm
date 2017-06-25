package de.ladis.infohm.core.parser.xml.publisher;

import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;

import org.joda.time.DateTime;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.domain.PublisherParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlPublisherParser extends XmlParser<Publisher> implements PublisherParser {

	@Override
	protected Publisher parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		return parsePublisher(parser);
	}

	public static Publisher parsePublisher(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "publisher");

		Long id = null;
		String name = null;
		String description = null;
		DateTime created = null;
		DateTime updated = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("id".equals(tag)) {
				id = nextLong(parser, "id");
			} else if ("name".equals(tag)) {
				name = nextString(parser, "name");
			} else if ("description".equals(tag)) {
				description = nextString(parser, "description");
			} else if ("createdAt".equals(tag)) {
				created = nextDateTime(parser, "createdAt");
			} else if ("updatedAt".equals(tag)) {
				updated = nextDateTime(parser, "updatedAt");
			} else {
				skipTag(parser);
			}
		}

		Publisher publisher = new Publisher();
		publisher.setId(id);
		publisher.setName(name);
		publisher.setDescription(description);
		publisher.setCreatedAt(created);
		publisher.setUpdatedAt(updated);

		parser.require(XmlPullParser.END_TAG, null, "publisher");

		return publisher;
	}

}

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
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("id".equals(tag)) {
				id = parseId(parser);
			} else if ("name".equals(tag)) {
				name = parseName(parser);
			} else if ("description".equals(tag)) {
				description = parseDescription(parser);
			} else if ("createdAt".equals(tag)) {
				created = parseCreatedAt(parser);
			} else if ("updatedAt".equals(tag)) {
				updated = parseUpdatedAt(parser);
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

	public static String parseName(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "name");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		String name = textOf(value);

		parser.require(XmlPullParser.END_TAG, null, "name");

		return name;
	}

	public static String parseDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "description");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		String description = textOf(value);

		parser.require(XmlPullParser.END_TAG, null, "description");

		return description;
	}

}

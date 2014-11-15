package de.ladis.infohm.core.parser.xml.publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.domain.PublishersParser;
import de.ladis.infohm.core.parser.xml.XmlParser;
import de.ladis.infohm.util.XmlParserUtil;

public class XmlPublishersParser extends XmlParser<List<Publisher>> implements PublishersParser {

	@Override
	protected List<Publisher> parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "publishers");

		List<Publisher> publishers = new ArrayList<Publisher>();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("publisher".equals(tag)) {
				publishers.add(parsePublisher(parser));
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "publishers");

		return publishers;
	}

	private Publisher parsePublisher(XmlPullParser parser) throws XmlPullParserException, IOException {
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
				XmlParserUtil.skipTag(parser);
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

	private Long parseId(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "id");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		Long id = Long.parseLong(value);

		parser.require(XmlPullParser.END_TAG, null, "id");

		return id;
	}

	private String parseName(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "name");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		parser.require(XmlPullParser.END_TAG, null, "name");

		return value;
	}

	private String parseDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "description");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		parser.require(XmlPullParser.END_TAG, null, "description");

		return value;
	}

	private DateTime parseCreatedAt(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "createdAt");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		parser.require(XmlPullParser.END_TAG, null, "createdAt");

		DateTime created = DateTime.parse(value);

		return created;
	}

	private DateTime parseUpdatedAt(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "updatedAt");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		parser.require(XmlPullParser.END_TAG, null, "updatedAt");

		DateTime updated = DateTime.parse(value);

		return updated;
	}

}

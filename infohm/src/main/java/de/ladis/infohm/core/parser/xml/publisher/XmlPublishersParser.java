package de.ladis.infohm.core.parser.xml.publisher;

import static de.ladis.infohm.core.parser.xml.publisher.XmlPublisherParser.*;
import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.domain.PublishersParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

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
			} else {
				skipTag(parser);
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "publishers");

		return publishers;
	}

}

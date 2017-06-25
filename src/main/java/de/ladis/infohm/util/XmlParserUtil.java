package de.ladis.infohm.util;

import java.io.IOException;

import org.joda.time.DateTime;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlParserUtil {

	public static Long nextLong(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, tag);

		Long value = Long.parseLong(readText(parser));

		parser.require(XmlPullParser.END_TAG, null, tag);

		return value;
	}

	public static Double nextDouble(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, tag);

		Double value = Double.parseDouble(readText(parser));

		parser.require(XmlPullParser.END_TAG, null, tag);

		return value;
	}

	public static String nextString(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, tag);

		String value = readText(parser);

		parser.require(XmlPullParser.END_TAG, null, tag);

		return value;
	}

	public static DateTime nextDateTime(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, tag);

		DateTime value = DateTime.parse(readText(parser));

		parser.require(XmlPullParser.END_TAG, null, tag);

		return value;
	}

	private static String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = textOf(text);
		}

		return value;
	}

	private static String textOf(String text) {
		return text.trim().replace("\r", "");
	}

	public static void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}

		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_DOCUMENT:
				throw new XmlPullParserException("reached unexpected end of document");
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

}

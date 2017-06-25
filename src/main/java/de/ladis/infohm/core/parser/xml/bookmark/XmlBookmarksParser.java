package de.ladis.infohm.core.parser.xml.bookmark;

import static de.ladis.infohm.core.parser.xml.bookmark.XmlBookmarkParser.*;
import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.parser.domain.BookmarksParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlBookmarksParser extends XmlParser<List<Bookmark>> implements BookmarksParser {

	@Override
	protected List<Bookmark> parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "bookmarks");

		List<Bookmark> bookmarks = new ArrayList<Bookmark>();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("bookmark".equals(tag)) {
				bookmarks.add(parseBookmark(parser));
			} else {
				skipTag(parser);
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "bookmarks");

		return bookmarks;
	}

}

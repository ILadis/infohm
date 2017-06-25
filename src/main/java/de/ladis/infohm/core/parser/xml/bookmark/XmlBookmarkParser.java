package de.ladis.infohm.core.parser.xml.bookmark;

import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;

import org.joda.time.DateTime;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.parser.domain.BookmarkParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlBookmarkParser extends XmlParser<Bookmark> implements BookmarkParser {

	@Override
	protected Bookmark parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		return parseBookmark(parser);
	}

	public static Bookmark parseBookmark(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "bookmark");

		Long id = null;
		String title = null;
		String description = null;
		String url = null;
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
			} else if ("title".equals(tag)) {
				title = nextString(parser, "title");
			} else if ("description".equals(tag)) {
				description = nextString(parser, "description");
			} else if ("url".equals(tag)) {
				url = nextString(parser, "url");
			} else if ("createdAt".equals(tag)) {
				created = nextDateTime(parser, "createdAt");
			} else if ("updatedAt".equals(tag)) {
				updated = nextDateTime(parser, "updatedAt");
			} else {
				skipTag(parser);
			}
		}

		Bookmark bookmark = new Bookmark();
		bookmark.setId(id);
		bookmark.setTitle(title);
		bookmark.setUrl(url);
		bookmark.setDescription(description);
		bookmark.setCreatedAt(created);
		bookmark.setUpdatedAt(updated);

		parser.require(XmlPullParser.END_TAG, null, "bookmark");

		return bookmark;
	}

}

package de.ladis.infohm.core.parser.xml.bookmark;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;

import de.ladis.infohm.core.domain.Bookmark;

public class XmlBookmarksTestUtil {

	public static InputStream validResourceAsStream() {
		return XmlBookmarksTestUtil.class.getResourceAsStream("bookmarks_valid.xml");
	}

	public static InputStream invalidResourceAsStream() {
		return XmlBookmarksTestUtil.class.getResourceAsStream("bookmarks_invalid.xml");
	}

	public static InputStream extendedResourceAsStream() {
		return XmlBookmarksTestUtil.class.getResourceAsStream("bookmarks_extended.xml");
	}

	public static Bookmark newInstance() {
		Bookmark bookmark = new Bookmark();
		bookmark.setTitle("new bookmark");
		bookmark.setDescription("this is a new bookmark that should not exist");
		bookmark.setUrl("http://i-do-not-exists.com/");

		return bookmark;
	}

	public static void assertValid(List<Bookmark> results) {
		Bookmark expected;

		assertThat(results.size(), is(2));

		expected = new Bookmark();
		expected.setId(Long.valueOf(1l));
		expected.setTitle("first bookmark");
		expected.setDescription("our first bookmark");
		expected.setUrl("http://our-first-bookmar.com/");
		expected.setCreatedAt(new DateTime(2014, 12, 4, 20, 18, 0));
		expected.setUpdatedAt(new DateTime(2014, 12, 4, 20, 22, 0));

		assertEqual(results.get(0), expected);

		expected = new Bookmark();
		expected.setId(Long.valueOf(2l));
		expected.setTitle("second bookmark");
		expected.setDescription("the second bookmark");
		expected.setUrl("http://the-second-bookmark.com/");
		expected.setCreatedAt(new DateTime(2014, 12, 4, 20, 19, 0));
		expected.setUpdatedAt(new DateTime(2014, 12, 4, 20, 19, 0));

		assertEqual(results.get(1), expected);
	}

	public static void assertEqual(Bookmark actual, Bookmark expected) {
		assertThat(actual.getId(), equalTo(expected.getId()));
		assertThat(actual.getTitle(), equalTo(expected.getTitle()));
		assertThat(actual.getDescription(), equalTo(expected.getDescription()));
		assertThat(actual.getUrl(), equalTo(expected.getUrl()));
		assertTrue(actual.getCreatedAt().isEqual(expected.getCreatedAt()));
		assertTrue(actual.getUpdatedAt().isEqual(expected.getUpdatedAt()));
	}

}

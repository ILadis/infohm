package de.ladis.infohm.core.parser.xml.bookmark;

import static de.ladis.infohm.core.parser.xml.bookmark.XmlBookmarksTestUtil.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.parser.ParserException;
import de.ladis.infohm.core.parser.domain.BookmarksParser;
import de.ladis.infohm.core.parser.xml.XmlParserTestModule;
import de.ladis.infohm.test.BaseTest;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class XmlBookmarksParserTest extends BaseTest {

	@Override
	protected List<Object> getModules() {
		return Arrays.<Object>asList(
				new XmlParserTestModule()
		);
	}

	@Inject
	protected BookmarksParser parser;

	@Test
	public void parserShouldParseValidXmlSuccessful() {
		InputStream stream = validResourceAsStream();

		List<Bookmark> results = parser.parse(stream);

		assertValid(results);
	}

	@Test
	public void parserShouldParseExtendedButValidXmlSuccessful() {
		InputStream stream = extendedResourceAsStream();

		List<Bookmark> results = parser.parse(stream);

		assertValid(results);
	}

	@Test
	public void parserShouldFailWhenParsingNotWellFormedXml() {
		InputStream stream = invalidResourceAsStream();

		try {
			parser.parse(stream);

			fail("XmlBookmarksParser.parse() did not throw expected exception");
		} catch (ParserException e) {
			assertThat(e.getCause(), instanceOf(XmlPullParserException.class));
		}
	}

}

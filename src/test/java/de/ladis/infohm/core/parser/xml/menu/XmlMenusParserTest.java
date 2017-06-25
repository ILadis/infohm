package de.ladis.infohm.core.parser.xml.menu;

import static de.ladis.infohm.core.parser.xml.menu.XmlMenusTestUtil.*;
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

import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.parser.ParserException;
import de.ladis.infohm.core.parser.domain.MenusParser;
import de.ladis.infohm.core.parser.xml.XmlParserTestModule;
import de.ladis.infohm.test.BaseTest;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class XmlMenusParserTest extends BaseTest {

	@Override
	protected List<Object> getModules() {
		return Arrays.<Object>asList(
				new XmlParserTestModule()
		);
	}

	@Inject
	protected MenusParser parser;

	@Test
	public void parserShouldParseValidXmlSuccessful() {
		InputStream stream = validResourceAsStream();

		List<Menu> results = parser.parse(stream);

		assertValid(results);
	}

	@Test
	public void parserShouldParseExtendedButValidXmlSuccessful() {
		InputStream stream = extendedResourceAsStream();

		List<Menu> results = parser.parse(stream);

		assertValid(results);
	}

	@Test
	public void parserShouldFailWhenParsingNotWellFormedXml() {
		InputStream stream = invalidResourceAsStream();

		try {
			parser.parse(stream);

			fail("XmlMenusParser.parse() did not throw expected exception");
		} catch (ParserException e) {
			assertThat(e.getCause(), instanceOf(XmlPullParserException.class));
		}
	}

}

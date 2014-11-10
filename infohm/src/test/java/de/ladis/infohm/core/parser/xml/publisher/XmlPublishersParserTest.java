package de.ladis.infohm.core.parser.xml.publisher;

import static de.ladis.infohm.core.parser.xml.publisher.XmlPublishersTestUtil.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.domain.PublishersParser;
import de.ladis.infohm.test.BaseTest;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class  XmlPublishersParserTest extends BaseTest {

	@Override
	protected List<Object> getModules() {
		return Arrays.<Object>asList(
				new XmlPublishersParserTestModule()
		);
	}

	@Inject
	protected InputStream stream;

	@Inject
	protected PublishersParser parser;

	@Test
	public void parserShouldParseWellFormedXmlSuccessful() throws Exception {
		List<Publisher> results = parser.parse(stream);

		assertValid(results);
	}

}

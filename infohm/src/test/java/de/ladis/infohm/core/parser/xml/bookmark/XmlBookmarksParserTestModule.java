package de.ladis.infohm.core.parser.xml.bookmark;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.parser.domain.BookmarksParser;

@Module(
library = true,
injects = {
		XmlBookmarksParserTest.class
})
public class XmlBookmarksParserTestModule {

	@Provides
	@Singleton
	public BookmarksParser provideBookmarksParser() {
		return new XmlBookmarksParser();
	}

}

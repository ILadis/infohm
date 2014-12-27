package de.ladis.infohm.core.parser.xml;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.parser.domain.BookmarksParser;
import de.ladis.infohm.core.parser.domain.CafeteriasParser;
import de.ladis.infohm.core.parser.domain.EventsParser;
import de.ladis.infohm.core.parser.domain.PublishersParser;
import de.ladis.infohm.core.parser.xml.bookmark.XmlBookmarksParser;
import de.ladis.infohm.core.parser.xml.bookmark.XmlBookmarksParserTest;
import de.ladis.infohm.core.parser.xml.cafeteria.XmlCafeteriasParser;
import de.ladis.infohm.core.parser.xml.cafeteria.XmlCafeteriasParserTest;
import de.ladis.infohm.core.parser.xml.event.XmlEventsParser;
import de.ladis.infohm.core.parser.xml.event.XmlEventsParserTest;
import de.ladis.infohm.core.parser.xml.publisher.XmlPublishersParser;
import de.ladis.infohm.core.parser.xml.publisher.XmlPublishersParserTest;

@Module(
library = true,
injects = {
		XmlBookmarksParserTest.class,
		XmlEventsParserTest.class,
		XmlPublishersParserTest.class,
		XmlCafeteriasParserTest.class,
})
public class XmlParserTestModule {

	@Provides
	@Singleton
	public BookmarksParser provideBookmarksParser() {
		return new XmlBookmarksParser();
	}

	@Provides
	@Singleton
	public EventsParser provideEventsParser() {
		return new XmlEventsParser();
	}

	@Provides
	@Singleton
	public PublishersParser providePublishersParser() {
		return new XmlPublishersParser();
	}

	@Provides
	@Singleton
	public CafeteriasParser provideCafeteriasParser() {
		return new XmlCafeteriasParser();
	}

}

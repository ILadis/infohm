package de.ladis.infohm.core.parser.xml.event;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;

import de.ladis.infohm.core.domain.Event;

public class XmlEventsTestUtil {

	public static InputStream validResourceAsStream() {
		return XmlEventsTestUtil.class.getResourceAsStream("events_valid.xml");
	}

	public static InputStream invalidResourceAsStream() {
		return XmlEventsTestUtil.class.getResourceAsStream("events_invalid.xml");
	}

	public static InputStream extendedResourceAsStream() {
		return XmlEventsTestUtil.class.getResourceAsStream("events_extended.xml");
	}

	public static Event newInstance() {
		Event event = new Event();
		event.setHeadline("new event");
		event.setContent("this is a new event that should not exist");

		return event;
	}

	public static void assertValid(List<Event> results) {
		Event expected;

		assertThat(results.size(), is(2));

		expected = new Event();
		expected.setId(Long.valueOf(1l));
		expected.setHeadline("First Event");
		expected.setContent("This is the first event of the test publisher. As always supposed for testing purposes.");
		expected.setCreatedAt(new DateTime(2014, 11, 6, 18, 22, 0));
		expected.setUpdatedAt(new DateTime(2014, 11, 6, 18, 22, 0));

		assertEqual(results.get(0), expected);

		expected = new Event();
		expected.setId(Long.valueOf(2l));
		expected.setHeadline("Second Event");
		expected.setContent("Another event, this time with some different content.");
		expected.setCreatedAt(new DateTime(2014, 11, 7, 12, 11, 0));
		expected.setUpdatedAt(new DateTime(2014, 11, 7, 12, 11, 0));

		assertEqual(results.get(1), expected);
	}

	public static void assertEqual(Event actual, Event expected) {
		assertThat(actual.getId(), equalTo(expected.getId()));
		assertThat(actual.getHeadline(), equalTo(expected.getHeadline()));
		assertThat(actual.getContent(), equalTo(expected.getContent()));
		assertTrue(actual.getCreatedAt().isEqual(expected.getCreatedAt()));
		assertTrue(actual.getUpdatedAt().isEqual(expected.getUpdatedAt()));
	}

}

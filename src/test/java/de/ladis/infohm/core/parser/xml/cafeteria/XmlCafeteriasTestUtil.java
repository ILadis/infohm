package de.ladis.infohm.core.parser.xml.cafeteria;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;

import de.ladis.infohm.core.domain.Cafeteria;

public class XmlCafeteriasTestUtil {

	public static InputStream validResourceAsStream() {
		return XmlCafeteriasTestUtil.class.getResourceAsStream("cafeterias_valid.xml");
	}

	public static InputStream invalidResourceAsStream() {
		return XmlCafeteriasTestUtil.class.getResourceAsStream("cafeterias_invalid.xml");
	}

	public static InputStream extendedResourceAsStream() {
		return XmlCafeteriasTestUtil.class.getResourceAsStream("cafeterias_extended.xml");
	}

	public static Cafeteria newInstance() {
		Cafeteria cafeteria = new Cafeteria();
		cafeteria.setName("new cafeteria");
		cafeteria.setLongitude(1.337);
		cafeteria.setLatitude(47.11);

		return cafeteria;
	}

	public static void assertValid(List<Cafeteria> results) {
		Cafeteria expected;

		assertThat(results.size(), is(2));

		expected = new Cafeteria();
		expected.setId(Long.valueOf(1l));
		expected.setName("test-cafeteria");
		expected.setLongitude(Double.valueOf(1.0));
		expected.setLatitude(Double.valueOf(1.0));
		expected.setCreatedAt(new DateTime(2014, 12, 27, 12, 52, 33));
		expected.setUpdatedAt(new DateTime(2014, 12, 27, 12, 52, 33));

		assertEqual(results.get(0), expected);

		expected = new Cafeteria();
		expected.setId(Long.valueOf(2l));
		expected.setName("#1 cafeteria");
		expected.setLongitude(Double.valueOf(3.5));
		expected.setLatitude(Double.valueOf(2));
		expected.setCreatedAt(new DateTime(2014, 12, 27, 12, 53, 17));
		expected.setUpdatedAt(new DateTime(2014, 12, 27, 12, 53, 18));

		assertEqual(results.get(1), expected);
	}

	public static void assertEqual(Cafeteria actual, Cafeteria expected) {
		assertThat(actual.getId(), equalTo(expected.getId()));
		assertThat(actual.getName(), equalTo(expected.getName()));
		assertThat(actual.getLongitude(), equalTo(expected.getLongitude()));
		assertThat(actual.getLatitude(), equalTo(expected.getLatitude()));
		assertTrue(actual.getCreatedAt().isEqual(expected.getCreatedAt()));
		assertTrue(actual.getUpdatedAt().isEqual(expected.getUpdatedAt()));
	}

}

package de.ladis.infohm.core.dao.http.publisher;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.test.BaseTest;

public class PublisherHttpDaoTest extends BaseTest {

	@Override
	protected List<Object> getModules() {
		return Arrays.<Object>asList(
				new PublisherHttpDaoTestModule()
		);
	}

	@Inject
	protected PublisherHttpDao dao;

	@Test
	public void httpDaoShouldFetchAndParseValidHttpResponseSuccessful() throws Exception {
		Publisher expected = new Publisher();
		expected.setId(1l);
		expected.setName("test");
		expected.setDescription("a publisher for testing purposes");

		List<Publisher> results = dao.list();

		assertThat(results.size(), is(1));
		assertThat(results.get(0), equalTo(expected));
	}
}

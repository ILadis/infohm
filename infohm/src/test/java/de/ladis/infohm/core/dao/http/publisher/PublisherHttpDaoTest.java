package de.ladis.infohm.core.dao.http.publisher;

import static de.ladis.infohm.core.parser.xml.publisher.XmlPublishersTestUtil.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.http.MockedHttpClient;
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
	protected MockedHttpClient client;

	@Inject
	protected PublisherHttpDao dao;

	@Test
	public void httpDaoShouldFetchAndParseValidHttpResponseSuccessful() {
		client.setResponseStatusCode(200);
		client.setResponseContentType("application/xml");
		client.setResponseStream(validResourceAsStream());

		List<Publisher> results = dao.list();

		assertValid(results);
	}

	@Test
	public void httpDaoShouldNotSupportTheseMethodsAndThrowExpectedException() {
		try {
			dao.find(1l);

			fail("PublisherHttpDao.find() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		Publisher publisher = newInstance();

		try {
			dao.insert(publisher);

			fail("PublisherHttpDao.insert() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.update(publisher);

			fail("PublisherHttpDao.update() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.delete(publisher);

			fail("PublisherHttpDao.delete() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.starred();

			fail("PublisherHttpDao.starred() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.star(publisher);

			fail("PublisherHttpDao.star() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.unstarAll();

			fail("PublisherHttpDao.unstarAll() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}

		try {
			dao.unstar(publisher);

			fail("PublisherHttpDao.unstar() did not throw expected exception");
		} catch (DaoException e) {
			assertThat(e.getCause(), instanceOf(UnsupportedOperationException.class));
		}
	}

}

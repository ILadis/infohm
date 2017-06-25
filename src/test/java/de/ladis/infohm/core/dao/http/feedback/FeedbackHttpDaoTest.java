package de.ladis.infohm.core.dao.http.feedback;

import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ladis.infohm.core.dao.http.HttpDaoTestModule;
import de.ladis.infohm.core.domain.Feedback;
import de.ladis.infohm.test.BaseTest;
import de.ladis.infohm.test.mock.MockedHttpClient;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class FeedbackHttpDaoTest extends BaseTest {

	@Override
	protected List<Object> getModules() {
		return Arrays.<Object>asList(
				new HttpDaoTestModule()
		);
	}

	@Inject
	protected MockedHttpClient client;

	@Inject
	protected FeedbackHttpDao dao;

	@Test
	public void httpDaoShouldPostExpectedFeedbackContent() {
		client.willRespondWith()
				.statusCode(200);

		Feedback feedback = new Feedback();
		feedback.setSubject("test subject");
		feedback.setMessage("this is a test message");
		feedback.setAnonymous(true);

		dao.insert(feedback);

		String body = client.fromLastRequest()
				.readBody();

		assertThat(body, notNullValue());
		assertThat(body, equalTo("subject=test+subject&body=this+is+a+test+message&anonym=true"));
	}

}

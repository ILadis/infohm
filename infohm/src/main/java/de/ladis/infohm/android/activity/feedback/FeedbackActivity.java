package de.ladis.infohm.android.activity.feedback;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.controller.FeedbackController;
import de.ladis.infohm.core.domain.Feedback;
import de.ladis.infohm.core.service.FeedbackService;

public class FeedbackActivity extends BaseActivity implements FeedbackController {

	@Inject
	protected FeedbackService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.actionbar_home_as_up);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void submit(String subject, String message) {
		Feedback feedback = new Feedback();
		feedback.setSubject(subject);
		feedback.setMessage(message);
		feedback.setAnonymous(true);

		service.submitNew(feedback).doAsync();
	}

}

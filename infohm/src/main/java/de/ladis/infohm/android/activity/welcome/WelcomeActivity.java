package de.ladis.infohm.android.activity.welcome;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;
import android.content.Intent;
import android.os.Bundle;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.activity.events.MainActivity;
import de.ladis.infohm.android.controller.StarPublisherController;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.service.PublisherService;

public class WelcomeActivity extends BaseActivity implements StarPublisherController {

	@Inject
	protected PublisherService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}

	@Override
	public void star(List<Publisher> publishers) {
		service.unstarFromAll().doSync();

		for (Publisher publisher : publishers) {
			service.starTo(publisher).doSync();
		}
	}

	@OnClick(R.id.activity_welcome_submit)
	public void submit() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}

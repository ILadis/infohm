package de.ladis.infohm.android.activity.publisher;

import java.util.List;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.controller.StarPublisherController;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.service.PublisherService;

public class StarPublisherActivity extends BaseActivity implements StarPublisherController {

	@Inject
	protected PublisherService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_star_publisher);

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
	public void star(List<Publisher> publishers) {
		service.unstarFromAll().doSync();

		for (Publisher publisher : publishers) {
			service.starTo(publisher).doSync();
		}
	}

}

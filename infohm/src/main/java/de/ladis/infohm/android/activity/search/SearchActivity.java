package de.ladis.infohm.android.activity.search;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.controller.SearchController;
import de.ladis.infohm.core.service.SearchService;

public class SearchActivity extends BaseActivity implements SearchController {

	@Inject
	protected SearchService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

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
	public void onQueryChanged(String query) {
		service.searchFor(query).doAsync();
	}

	@Override
	public void onQuerySubmitted(String query) {
	}

	@Override
	public void onQueryClosed() {
		finish();
	}

}

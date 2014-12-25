package de.ladis.infohm.android.activity.search;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;

public class SearchActivity extends BaseActivity {

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

}

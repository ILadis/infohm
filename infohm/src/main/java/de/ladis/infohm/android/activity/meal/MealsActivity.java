package de.ladis.infohm.android.activity.meal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseActivity;
import de.ladis.infohm.android.fragment.meal.MealsPagerFragment;
import de.ladis.infohm.android.parcel.cafeteria.CafeteriaParcelHolder;
import de.ladis.infohm.core.domain.Cafeteria;

public class MealsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meals);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.actionbar_home_as_up);

		Intent intent = getIntent();
		FragmentManager manager = getSupportFragmentManager();

		if (intent == null || !intent.hasExtra("cafeteria")) {
			finish();
		} else if (manager.findFragmentById(R.id.activity_meals_fragment) == null) {
			CafeteriaParcelHolder holder = intent.getParcelableExtra("cafeteria");
			Cafeteria cafeteria = holder.get();

			Fragment fragment = MealsPagerFragment.newInstance(cafeteria);

			manager.beginTransaction()
					.replace(R.id.activity_meals_fragment, fragment)
					.commit();

			setTitle(cafeteria.getName());
		}
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

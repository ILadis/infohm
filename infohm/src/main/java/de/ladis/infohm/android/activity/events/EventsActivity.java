package de.ladis.infohm.android.activity.events;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseDrawerActivity;
import de.ladis.infohm.android.controller.NavigationDrawerController;

public class EventsActivity extends BaseDrawerActivity implements NavigationDrawerController {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
	}

	@Override
	public int initialItem() {
		return R.id.fragment_navigation_drawer_item_newsfeed;
	}

	@Override
	public void selected(int item) {
		getDrawer().closeDrawer(GravityCompat.START);
	}

}

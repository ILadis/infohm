package de.ladis.infohm.android.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseDrawerActivity;
import de.ladis.infohm.android.activity.feedback.FeedbackActivity;
import de.ladis.infohm.android.controller.NavigationDrawerController;
import de.ladis.infohm.android.fragment.bookmarks.BookmarksFragment;
import de.ladis.infohm.android.fragment.events.EventsPagerFragment;

public class MainActivity extends BaseDrawerActivity implements NavigationDrawerController {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		switchFragment(EventsPagerFragment.class);
	}

	@Override
	public int initialItem() {
		Fragment current = curretFragment();

		if (EventsPagerFragment.class.isInstance(current)) {
			return R.id.fragment_navigation_drawer_item_newsfeed;
		} else if (BookmarksFragment.class.isInstance(current)) {
			return R.id.fragment_navigation_drawer_item_bookmarks;
		} else {
			return R.id.fragment_navigation_drawer_item_newsfeed;
		}
	}

	@Override
	public void selected(int item) {
		getDrawer().closeDrawer(GravityCompat.START);

		switch (item) {
		case R.id.fragment_navigation_drawer_item_newsfeed:
			switchFragment(EventsPagerFragment.class);
			break;
		case R.id.fragment_navigation_drawer_item_bookmarks:
			switchFragment(BookmarksFragment.class);
			break;
		case R.id.fragment_navigation_drawer_item_feedback:
			switchActivity(FeedbackActivity.class);
			break;
		}
	}

	private Fragment curretFragment() {
		FragmentManager manager = getSupportFragmentManager();

		return manager.findFragmentById(R.id.activity_main_content);
	}

	private <T extends Fragment> void switchFragment(Class<T> clazz) {
		FragmentManager manager = getSupportFragmentManager();
		Fragment fragment;

		try {
			fragment = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Fragment current = curretFragment();

		if (current != null) {
			if (clazz != current.getClass()) {
				manager.beginTransaction()
						.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
						.replace(R.id.activity_main_content, fragment)
						.commit();
			}
		} else {
			manager.beginTransaction()
					.add(R.id.activity_main_content, fragment)
					.commit();
		}
	}

	private <T extends Activity> void switchActivity(Class<T> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

}

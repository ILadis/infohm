package de.ladis.infohm.android.activity.main;

import static android.support.v4.widget.DrawerLayout.*;
import static android.support.v4.view.GravityCompat.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.BaseDrawerActivity;
import de.ladis.infohm.android.activity.feedback.FeedbackActivity;
import de.ladis.infohm.android.controller.NavigationController;
import de.ladis.infohm.android.controller.StartupController;
import de.ladis.infohm.android.fragment.bookmarks.BookmarksFragment;
import de.ladis.infohm.android.fragment.cafeteria.CafeteriasPagerFragment;
import de.ladis.infohm.android.fragment.events.EventsPagerFragment;
import de.ladis.infohm.android.fragment.startup.StartupFragment;

public class MainActivity extends BaseDrawerActivity implements StartupController, NavigationController {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			switchFragment(StartupFragment.class);
			getDrawer().setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
		}
	}

	@Override
	public void startupComplete() {
		switchFragment(EventsPagerFragment.class);
		getDrawer().setDrawerLockMode(LOCK_MODE_UNLOCKED);
	}

	@Override
	public int initialItem() {
		Fragment current = curretFragment();

		if (EventsPagerFragment.class.isInstance(current)) {
			return R.id.fragment_navigation_newsfeed;
		} else if (BookmarksFragment.class.isInstance(current)) {
			return R.id.fragment_navigation_bookmarks;
		} else if (CafeteriasPagerFragment.class.isInstance(current)) {
			return R.id.fragment_navigation_cafeteria;
		} else {
			return R.id.fragment_navigation_newsfeed;
		}
	}

	@Override
	public void selected(int item) {
		getDrawer().closeDrawer(START);

		switch (item) {
		case R.id.fragment_navigation_newsfeed:
			switchFragment(EventsPagerFragment.class);
			setTitle(R.string.fragment_navigation_newsfeed);
			break;
		case R.id.fragment_navigation_cafeteria:
			switchFragment(CafeteriasPagerFragment.class);
			setTitle(R.string.fragment_navigation_cafeteria);
			break;
		case R.id.fragment_navigation_bookmarks:
			switchFragment(BookmarksFragment.class);
			setTitle(R.string.fragment_navigation_bookmarks);
			break;
		case R.id.fragment_navigation_feedback:
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

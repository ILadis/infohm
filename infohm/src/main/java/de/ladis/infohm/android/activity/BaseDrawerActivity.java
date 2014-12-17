package de.ladis.infohm.android.activity;

import static android.support.v4.widget.DrawerLayout.*;
import static android.support.v4.view.GravityCompat.*;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import de.ladis.infohm.R;

public abstract class BaseDrawerActivity extends BaseActivity {

	private DrawerLayout drawer;
	private ActionBarDrawerToggle toggle;

	@Override
	public void setContentView(int layoutResId) {
		super.setContentView(layoutResId);
		setContentView();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		setContentView();
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		setContentView();
	}

	private final void setContentView() {
		drawer = (DrawerLayout) findViewById(R.id.activity_drawer);
		if (drawer != null) {
			ActionBar actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeButtonEnabled(true);

			toggle = new ActionBarDrawerToggle(this, drawer, getSupportToolbar(), R.string.app_name, R.string.app_name) {

				@Override
				public void onDrawerSlide(View drawerView, float slideOffset) {
					super.onDrawerSlide(drawerView, 0);
				}

			};
			drawer.setDrawerListener(toggle);
		}
	}

	public DrawerLayout getDrawer() {
		return drawer;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		toggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home && drawer.getDrawerLockMode(START) == LOCK_MODE_UNLOCKED) {
			if (drawer.isDrawerOpen(START)) {
				drawer.closeDrawer(START);
			} else {
				drawer.openDrawer(END);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (drawer.isDrawerOpen(START)) {
				drawer.closeDrawer(START);
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		toggle.onConfigurationChanged(newConfig);
	}

}

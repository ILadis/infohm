package de.ladis.infohm.android.adapter.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import de.ladis.infohm.android.fragment.drawer.NavigationDrawerFragment;
import de.ladis.infohm.core.domain.Publisher;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class EventsPagerAdapter extends FragmentPagerAdapter {

	private final List<Publisher> items;

	public EventsPagerAdapter(FragmentManager manager) {
		super(manager);

		this.items = new ArrayList<Publisher>();
	}

	public void addItems(Collection<Publisher> publishers) {
		for (Publisher publisher : publishers) {
			addItem(publisher);
		}
	}

	public void addItem(Publisher publisher) {
		int index = items.indexOf(publisher);

		if (index >= 0) {
			items.remove(index);
			items.add(index, publisher);
		} else {
			index = items.size();

			items.add(publisher);
		}

		notifyDataSetChanged();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return items.get(position).getName().toUpperCase(Locale.US);
	}

	@Override
	public Fragment getItem(int position) {
		return new NavigationDrawerFragment();
	}

	@Override
	public int getCount() {
		return items.size();
	}

}

package de.ladis.infohm.android.adapter.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.ladis.infohm.android.fragment.events.EventsFragment;
import de.ladis.infohm.android.fragment.events.EventsHighlightFragment;
import de.ladis.infohm.android.parcel.publisher.PublisherParcelHolder;
import de.ladis.infohm.core.domain.Publisher;

public class EventsPagerAdapter extends FragmentStatePagerAdapter {

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
			items.add(publisher);
		}

		notifyDataSetChanged();
	}

	public void clearItems() {
		items.clear();

		notifyDataSetChanged();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0) {
			return "HIGHLIGHTS";
		} else {
			return items.get(position-1).getName().toUpperCase(Locale.US);
		}
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new EventsHighlightFragment();
		} else {
			return EventsFragment.newInstance(items.get(position-1));
		}
	}

	@Override
	public int getItemPosition(Object item) {
		if (item instanceof EventsHighlightFragment) {
			return POSITION_UNCHANGED;
		} else if (item instanceof EventsFragment) {
			EventsFragment fragment = (EventsFragment) item;
			PublisherParcelHolder holder = fragment.getArguments().getParcelable("publisher");

			int position = items.indexOf(holder.get()) + 1;

			if (position > 0) {
				return position;
			}
		}

		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return items.size() + 1;
	}

}

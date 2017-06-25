package de.ladis.infohm.android.adapter.cafeteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.ladis.infohm.android.fragment.meal.DailyMealsFragment;
import de.ladis.infohm.android.parcel.cafeteria.CafeteriaParcelHolder;
import de.ladis.infohm.core.domain.Cafeteria;

public class CafeteriasPagerAdapter extends FragmentStatePagerAdapter {

	private final List<Cafeteria> items;

	public CafeteriasPagerAdapter(FragmentManager manager) {
		super(manager);

		this.items = new ArrayList<Cafeteria>();
	}

	public void addItems(Collection<Cafeteria> cafeterias) {
		for (Cafeteria cafeteria : cafeterias) {
			addItem(cafeteria);
		}
	}

	public void addItem(Cafeteria cafeteria) {
		int index = items.indexOf(cafeteria);

		if (index >= 0) {
			items.remove(index);
			items.add(index, cafeteria);
		} else {
			items.add(cafeteria);
		}

		notifyDataSetChanged();
	}

	public void clearItems() {
		items.clear();

		notifyDataSetChanged();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return items.get(position).getName().toUpperCase(Locale.US);
	}

	@Override
	public Fragment getItem(int position) {
		return DailyMealsFragment.newInstance(items.get(position));
	}

	@Override
	public int getItemPosition(Object item) {
		if (item instanceof DailyMealsFragment) {
			DailyMealsFragment fragment = (DailyMealsFragment) item;
			CafeteriaParcelHolder holder = fragment.getArguments().getParcelable("cafeteria");

			int position = items.indexOf(holder.get());

			if (position >= 0) {
				return position;
			}
		}

		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return items.size();
	}

}

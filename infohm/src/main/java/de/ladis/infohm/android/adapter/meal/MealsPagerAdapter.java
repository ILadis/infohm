package de.ladis.infohm.android.adapter.meal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.ladis.infohm.R;
import de.ladis.infohm.android.fragment.meal.MealsFragment;
import de.ladis.infohm.android.parcel.meal.MealsParcelHolder;
import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.util.TimestampFormatter;

public class MealsPagerAdapter extends FragmentStatePagerAdapter {

	private final Resources resources;
	private final TimestampFormatter formatter;
	private final List<Menu> items;

	public MealsPagerAdapter(FragmentManager manager, Context context) {
		super(manager);

		this.resources = context.getResources();
		this.formatter = TimestampFormatter.from(resources);
		this.items = new ArrayList<Menu>();
	}

	public void addItems(Collection<Menu> menus) {
		for (Menu menu : menus) {
			addItem(menu);
		}
	}

	public void addItem(Menu menu) {
		int index = items.indexOf(menu);

		if (index >= 0) {
			items.remove(index);
			items.add(index, menu);
		} else {
			items.add(menu);
		}

		notifyDataSetChanged();
	}

	public void clearItems() {
		items.clear();

		notifyDataSetChanged();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		DateTime date = items.get(position).getDate();

		String title = resources.getString(R.string.fragment_meals_pager_title,
				String.format("%02d", date.getMonthOfYear()),
				String.format("%02d", date.getDayOfMonth()));

		return formatter.format(title, date);
	}

	@Override
	public Fragment getItem(int position) {
		return MealsFragment.newInstance(items.get(position));
	}

	@Override
	public int getItemPosition(Object item) {
		if (item instanceof MealsFragment) {
			MealsFragment fragment = (MealsFragment) item;
			MealsParcelHolder holder = fragment.getArguments().getParcelable("meals");

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

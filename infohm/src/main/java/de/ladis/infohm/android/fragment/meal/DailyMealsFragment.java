package de.ladis.infohm.android.fragment.meal;

import static org.joda.time.Days.*;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.parcel.cafeteria.CafeteriaParcelHolder;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Meal;
import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.listener.MealListener;
import de.ladis.infohm.core.listener.SimpleMealListener;
import de.ladis.infohm.core.service.MealService;

public class DailyMealsFragment extends BaseFragment {

	public static DailyMealsFragment newInstance(Cafeteria cafeteria) {
		DailyMealsFragment fragment = new DailyMealsFragment();

		Bundle arguments = new Bundle();
		arguments.putParcelable("cafeteria", new CafeteriaParcelHolder(cafeteria));

		fragment.setArguments(arguments);

		return fragment;
	}

	@Inject
	protected MealService service;

	private Cafeteria cafeteria;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle arguments = getArguments();

		CafeteriaParcelHolder holder = arguments.getParcelable("cafeteria");
		cafeteria = holder.get();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new View(inflater.getContext());
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(listener);
		service.getForCurrentWeek(cafeteria).doAsync();
	}

	private MealListener listener = new SimpleMealListener() {

		@Override
		public void onCurrentWeek(Cafeteria cafeteria, List<Menu> menus) {
			if (menus != null) {
				findDailyMeals(menus);
			}
		}

	};

	private List<Meal> findDailyMeals(List<Menu> menus) {
		if (menus.size() > 0) {
			DateTime now = DateTime.now();
			Menu daily = menus.get(0);

			for (Menu menu : menus) {
				Days daysBetweenDailyAndNow = daysBetween(now, daily.getDate());
				Days daysBetweenMenuAndNow = daysBetween(now, menu.getDate());

				if (daysBetweenMenuAndNow.isLessThan(daysBetweenDailyAndNow) && daysBetweenMenuAndNow.getDays() <= 0) {
					daily = menu;
				}
			}

			return daily.getMeals();
		}

		return null;
	}

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

}

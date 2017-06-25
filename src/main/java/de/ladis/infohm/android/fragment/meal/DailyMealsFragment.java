package de.ladis.infohm.android.fragment.meal;

import static java.lang.Math.*;
import static org.joda.time.Minutes.*;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.meal.MealsActivity;
import de.ladis.infohm.android.animation.meal.DailyMealsAnimator;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.parcel.cafeteria.CafeteriaParcelHolder;
import de.ladis.infohm.android.widget.TimestampView;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Meal;
import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.listener.MealListener;
import de.ladis.infohm.core.listener.SimpleMealListener;
import de.ladis.infohm.core.service.MealService;

public class DailyMealsFragment extends BaseFragment implements OnRefreshListener {

	public static DailyMealsFragment newInstance(Cafeteria cafeteria) {
		DailyMealsFragment fragment = new DailyMealsFragment();

		Bundle arguments = new Bundle();
		arguments.putParcelable("cafeteria", new CafeteriaParcelHolder(cafeteria));

		fragment.setArguments(arguments);

		return fragment;
	}

	@Inject
	protected MealService service;

	@InjectView(R.id.fragment_daily_meals_refresh)
	protected SwipeRefreshLayout refreshView;

	@InjectView(R.id.fragment_daily_meals_headline)
	protected TimestampView headlineView;

	@InjectView(R.id.fragment_daily_meals_date)
	protected TextView dateView;

	@InjectView(R.id.fragment_daily_meals_offers)
	protected ViewGroup offersView;

	private Cafeteria cafeteria;
	private Menu menu;
	private DailyMealsAnimator animator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle arguments = getArguments();

		CafeteriaParcelHolder holder = arguments.getParcelable("cafeteria");
		cafeteria = holder.get();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_daily_meals, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		animator = new DailyMealsAnimator(this);
		animator.animateOneShots(savedInstanceState == null);

		refreshView.setOnRefreshListener(this);
		refreshView.setColorSchemeResources(R.color.actionbar_primary_color, R.color.actionbar_secondary_color);
		refreshView.setEnabled(false);
		refreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshView.setEnabled(true);
				refreshView.setRefreshing(service.isUpdating(cafeteria).doSync());
			}
		}, 1000);
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(listener);
		service.getForCurrentWeek(cafeteria).doAsync();
	}

	@Override
	public void onRefresh() {
		service.updateAll(cafeteria).doAsync();
	}

	private MealListener listener = new SimpleMealListener() {

		@Override
		public void onUpdated(Cafeteria target, List<Menu> menus) {
			if (cafeteria.equals(target)) {
				refreshView.setRefreshing(false);

				if (menus != null) {
					service.getForCurrentWeek(cafeteria).doAsync();
				}
			}
		}

		@Override
		public void onCurrentWeek(Cafeteria target, List<Menu> menus) {
			if (cafeteria.equals(target)) {
				menu = findDailyMeals(menus);

				if (menu != null && menu.getMeals().size() > 0) {
					offerDailyMeals(menu);

					animator.animateOffersAppearance();
					animator.animateNoContents(false);
				} else {
					service.getForNextWeek(cafeteria).doAsync();
				}
			}
		}

		@Override
		public void onNextWeek(Cafeteria target, List<Menu> menus) {
			if (cafeteria.equals(target) && menu == null) {
				menu = findDailyMeals(menus);

				if (menu != null && menu.getMeals().size() > 0) {
					offerDailyMeals(menu);

					animator.animateOffersAppearance();
					animator.animateNoContents(false);
				} else {
					animator.animateNoContents(true);
				}
			}
		}

	};

	private Menu findDailyMeals(List<Menu> menus) {
		if (menus != null && menus.size() > 0) {
			DateTime now = DateTime.now();
			Menu daily = menus.get(0);

			for (Menu menu : menus) {
				int minutesBetweenDailyAndNow = minutesBetween(now, daily.getDate()).getMinutes();
				int minutesBetweenMenuAndNow = minutesBetween(now, menu.getDate()).getMinutes();

				if (abs(minutesBetweenMenuAndNow) < abs(minutesBetweenDailyAndNow)) {
					if (minutesBetweenMenuAndNow > 0) {
						if (minutesBetweenMenuAndNow <= 2 * 60) {
							daily = menu;
						}
					} else {
						daily = menu;
					}
				}
			}

			return daily;
		}

		return null;
	}

	private void offerDailyMeals(Menu menu) {
		Context context = headlineView.getContext();

		DateTime date = menu.getDate();

		headlineView.setTimestamp(date);

		if (isToday(menu)) {
			headlineView.setText(R.string.fragment_daily_meals_headline_today);
		} else {
			headlineView.setText(R.string.fragment_daily_meals_headline_upcoming);
		}

		dateView.setText(getString(R.string.fragment_daily_meals_date,
				String.format("%04d", date.getYear()),
				String.format("%02d", date.getMonthOfYear()),
				String.format("%02d", date.getDayOfMonth())));

		offersView.removeAllViews();

		for (Meal meal : menu.getMeals()) {
			View view = View.inflate(context, R.layout.fragment_daily_meals_offer, null);

			TextView nameView = (TextView) view.findViewById(R.id.fragment_daily_meals_offer_name);
			TextView pricesView = (TextView) view.findViewById(R.id.fragment_daily_meals_offer_prices);

			nameView.setText(meal.getName());
			pricesView.setText(meal.getPrices().toString());

			offersView.addView(view);
		}
	}

	private boolean isCurrentWeek(Menu menu) {
		int now = DateTime.now().getWeekOfWeekyear();
		int actual = menu.getDate().getWeekOfWeekyear();

		return now - actual == 0;
	}

	private boolean isToday(Menu menu) {
		DateTime now = DateTime.now();
		DateTime date = menu.getDate();

		return now.withTimeAtStartOfDay().isEqual(date.withTimeAtStartOfDay());
	}

	@OnClick(R.id.fragment_daily_meals_more)
	protected void moreMeals(View view) {
		Context context = view.getContext();
		Parcelable cafeteria = getArguments().getParcelable("cafeteria");

		Intent intent = new Intent(context, MealsActivity.class);
		intent.putExtra("cafeteria", cafeteria);
		intent.putExtra("week", isCurrentWeek(menu));

		startActivity(intent);
	}

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

}

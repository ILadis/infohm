package de.ladis.infohm.android.fragment.meal;

import java.util.List;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.meal.MealsPagerAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.parcel.cafeteria.CafeteriaParcelHolder;
import de.ladis.infohm.android.widget.SlidingTabLayout;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.listener.MealListener;
import de.ladis.infohm.core.listener.SimpleMealListener;
import de.ladis.infohm.core.service.MealService;

public class MealsPagerFragment extends BaseFragment {

	public static MealsPagerFragment newInstance(Cafeteria cafeteria) {
		MealsPagerFragment fragment = new MealsPagerFragment();

		Bundle arguments = new Bundle();
		arguments.putParcelable("cafeteria", new CafeteriaParcelHolder(cafeteria));

		fragment.setArguments(arguments);

		return fragment;
	}

	@Inject
	protected MealService service;

	@InjectView(R.id.fragment_meals_pager_tabs)
	protected SlidingTabLayout tabView;

	@InjectView(R.id.fragment_meals_pager)
	protected ViewPager pagerView;

	private Cafeteria cafeteria;
	private MealsPagerAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle arguments = getArguments();

		CafeteriaParcelHolder holder = arguments.getParcelable("cafeteria");
		cafeteria = holder.get();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_meals_pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		adapter = new MealsPagerAdapter(getChildFragmentManager(), view.getContext());

		pagerView.setAdapter(adapter);
		tabView.setViewPager(pagerView);
	}

	@Override
	public void onResume() {
		super.onResume();

		adapter.clearItems();

		service.registerListener(listener);
		service.getForCurrentWeek(cafeteria).doAsync();
	}

	private final MealListener listener = new SimpleMealListener() {

		@Override
		public void onCurrentWeek(Cafeteria target, List<Menu> menus) {
			if (cafeteria.equals(target) && menus != null) {
				adapter.addItems(menus);
			}
		}

	};

}

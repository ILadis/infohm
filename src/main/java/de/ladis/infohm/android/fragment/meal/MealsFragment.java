package de.ladis.infohm.android.fragment.meal;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.meal.MealsAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.parcel.meal.MealsParcelHolder;
import de.ladis.infohm.core.domain.Meal;
import de.ladis.infohm.core.domain.Menu;

public class MealsFragment extends BaseFragment {

	public static MealsFragment newInstance(Menu menu) {
		MealsFragment fragment = new MealsFragment();

		Bundle arguments = new Bundle();
		arguments.putParcelable("meals", new MealsParcelHolder(menu));

		fragment.setArguments(arguments);

		return fragment;
	}

	@InjectView(R.id.fragment_meals_list)
	protected RecyclerView recyclerView;

	private List<Meal> meals;
	private MealsAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle arguments = getArguments();

		MealsParcelHolder holder = arguments.getParcelable("meals");
		meals = holder.get().getMeals();

		adapter = new MealsAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_meals, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		adapter.addItems(meals);
	}

}

package de.ladis.infohm.android.fragment.cafeteria;

import java.util.List;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.cafeteria.CafeteriasPagerAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.widget.SlidingTabLayout;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.listener.CafeteriaListener;
import de.ladis.infohm.core.listener.SimpleCafeteriaListener;
import de.ladis.infohm.core.service.CafeteriaService;

public class CafeteriasPagerFragment extends BaseFragment {

	@Inject
	protected CafeteriaService service;

	@InjectView(R.id.fragment_cafeterias_pager_tabs)
	protected SlidingTabLayout tabView;

	@InjectView(R.id.fragment_cafeterias_pager)
	protected ViewPager pagerView;

	private CafeteriasPagerAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_cafeterias_pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		adapter = new CafeteriasPagerAdapter(getChildFragmentManager());

		pagerView.setAdapter(adapter);
		tabView.setViewPager(pagerView);
	}

	@Override
	public void onResume() {
		super.onResume();

		adapter.clearItems();

		service.registerListener(listener);
		service.getAll().doAsync();
	}

	private final CafeteriaListener listener = new SimpleCafeteriaListener() {

		@Override
		public void onGathered(List<Cafeteria> cafeterias) {
			if (cafeterias != null) {
				adapter.addItems(cafeterias);
			}
		}

	};

}

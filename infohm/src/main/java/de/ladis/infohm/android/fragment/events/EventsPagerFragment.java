package de.ladis.infohm.android.fragment.events;

import java.util.List;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.events.EventsPagerAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.widget.SlidingTabLayout;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.PublisherListener;
import de.ladis.infohm.core.service.PublisherService;

public class EventsPagerFragment extends BaseFragment implements PublisherListener {

	@Inject
	protected PublisherService service;

	@InjectView(R.id.fragment_events_pager_tabs)
	protected SlidingTabLayout tabView;

	@InjectView(R.id.fragment_events_pager)
	protected ViewPager pagerView;

	private EventsPagerAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new EventsPagerAdapter(getChildFragmentManager());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_events_pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		pagerView.setAdapter(adapter);
		tabView.setViewPager(pagerView);
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(this);
		service.getStarred().doAsync();
	}

	@Override
	public void onUpdated(List<Publisher> publishers) {
	}

	@Override
	public void onGathered(List<Publisher> publishers) {
	}

	@Override
	public void onStarred(List<Publisher> publishers) {
		adapter.addItems(publishers);
	}

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(this);
	}

}

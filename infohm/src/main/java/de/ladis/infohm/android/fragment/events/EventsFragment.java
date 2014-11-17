package de.ladis.infohm.android.fragment.events;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

import com.google.common.collect.Range;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.events.EventsAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.parcel.publisher.PublisherParcelHolder;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.EventListener;
import de.ladis.infohm.core.service.EventService;

public class EventsFragment extends BaseFragment implements OnRefreshListener, EventListener {

	public static EventsFragment newInstance(Publisher publisher) {
		EventsFragment fragment = new EventsFragment();

		Bundle arguments = new Bundle();
		arguments.putParcelable("publisher", new PublisherParcelHolder(publisher));

		fragment.setArguments(arguments);

		return fragment;
	}

	@Inject
	protected EventService service;

	@InjectView(R.id.fragment_events_refresh)
	protected SwipeRefreshLayout refreshView;

	@InjectView(R.id.fragment_events_list)
	protected RecyclerView recyclerView;

	private Publisher publisher;
	private EventsAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle arguments = getArguments();

		PublisherParcelHolder holder = arguments.getParcelable("publisher");
		publisher = holder.get();

		adapter = new EventsAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_events, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		refreshView.setOnRefreshListener(this);
		refreshView.setColorSchemeResources(R.color.actionbar_primary_color, R.color.actionbar_secondary_color);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(this);
		service.getAll(publisher, Range.closed(0, 9)).doAsync();
	}

	@Override
	public void onRefresh() {
		service.updateAll(publisher).doAsync();
	}

	@Override
	public void onGathered(Publisher publisher, List<Event> events) {
		if (this.publisher.equals(publisher)) {
			if (events.size() <= 0) {
				service.updateAll(publisher).doAsync();
			} else {
				adapter.addItems(events);
			}
		}
	}

	@Override
	public void onUpdated(Publisher publisher, List<Event> events) {
		if (this.publisher.equals(publisher)) {
			refreshView.setRefreshing(false);
			adapter.addItems(events);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(this);
	}

}

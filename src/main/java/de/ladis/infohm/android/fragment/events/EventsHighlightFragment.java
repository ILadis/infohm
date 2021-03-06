package de.ladis.infohm.android.fragment.events;

import java.util.List;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;

import com.google.common.collect.Range;

import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.events.EventsAdapter;
import de.ladis.infohm.android.animation.events.EventsAnimator;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.SimpleEventListener;
import de.ladis.infohm.core.service.EventService;
import de.ladis.infohm.core.service.PublisherService;

public class EventsHighlightFragment extends BaseFragment {

	@Inject
	protected EventService eventService;

	@Inject
	protected PublisherService publisherService;

	@InjectView(R.id.fragment_events_refresh)
	protected SwipeRefreshLayout refreshView;

	@InjectView(R.id.fragment_events_list)
	protected RecyclerView recyclerView;

	private EventsAdapter adapter;
	private EventsAnimator animator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new EventsAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_events, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		animator = new EventsAnimator(this);
		animator.animateOneShots(savedInstanceState == null);

		refreshView.setEnabled(false);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	@Override
	public void onResume() {
		super.onResume();

		eventService.registerListener(listener);
		eventService.getHighlights(Range.closed(0, 9)).doAsync();
	}

	private final SimpleEventListener listener = new SimpleEventListener() {

		@Override
		public void onUpdated(Publisher publisher, List<Event> events) {
			eventService.getHighlights(Range.closed(0, 9)).doAsync();
		}

		@Override
		public void onHighlights(List<Event> events) {
			if (events != null) {
				adapter.addItems(events);
			}

			animator.animateItems();
			animator.animateNoContents(adapter.getItemCount() <= 0);
		}

	};

	@Override
	public void onPause() {
		super.onPause();

		eventService.unregisterListener(listener);
	}

}

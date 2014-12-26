package de.ladis.infohm.android.fragment.events;

import static android.view.View.*;

import java.util.List;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;

import com.google.common.collect.Range;

import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.events.EventsAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.parcel.publisher.PublisherParcelHolder;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.SimpleEventListener;
import de.ladis.infohm.core.service.EventService;

public class EventsFragment extends BaseFragment implements OnRefreshListener {

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

	@InjectView(R.id.fragment_events_no_content)
	protected View noContentView;

	private Publisher publisher;
	private EventsAdapter adapter;
	private Boolean animate;

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
		refreshView.setEnabled(false);
		refreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshView.setEnabled(true);
				refreshView.setRefreshing(service.isUpdating(publisher).doSync());
			}
		}, 1000);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		animate = savedInstanceState == null;
	}

	@Override
	public void onResume() {
		super.onResume();

		adapter.clearItems();

		service.registerListener(listener);
		service.getAll(publisher, Range.closed(0, 9)).doAsync();
	}

	@Override
	public void onRefresh() {
		if (!service.isUpdating(publisher).doSync()) {
			service.updateAll(publisher).doAsync();
		}
	}

	private final SimpleEventListener listener = new SimpleEventListener() {

		@Override
		public void onGathered(Publisher target, List<Event> events) {
			onUpdated(target, events);
		}

		@Override
		public void onUpdated(Publisher target, List<Event> events) {
			if (publisher.equals(target)) {
				refreshView.setRefreshing(false);

				if (events != null) {
					adapter.addItems(events);
				}
			}

			if (animate) {
				animate = false;
				animateItems();
			}

			showNoContentView(adapter.getItemCount() <= 0);
		}

	};

	private void animateItems() {
		recyclerView.post(new Runnable() {

			@Override
			public void run() {
				int count = recyclerView.getChildCount();

				for (int i = 0; i < count; i++) {
					View child = recyclerView.getChildAt(i);

					ViewCompat.setTranslationY(child, 100);
					ViewCompat.setAlpha(child, 0);

					ViewCompat.animate(child)
							.translationY(0)
							.alpha(1)
							.setStartDelay(i * 120)
							.setDuration(300)
							.start();
				}
			}

		});
	}

	private void showNoContentView(boolean show) {
		if (show) {
			if (noContentView.getVisibility() != VISIBLE) {
				ViewCompat.setAlpha(noContentView, 0);
				ViewCompat.animate(noContentView)
						.alpha(1)
						.start();
			}

			noContentView.setVisibility(VISIBLE);
		} else {
			noContentView.setVisibility(GONE);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

}

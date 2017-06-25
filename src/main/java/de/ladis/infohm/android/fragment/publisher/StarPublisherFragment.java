package de.ladis.infohm.android.fragment.publisher;

import java.util.List;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.publisher.StarPublisherAdapter;
import de.ladis.infohm.android.controller.StarPublisherController;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.SimplePublisherListener;
import de.ladis.infohm.core.service.PublisherService;

public class StarPublisherFragment extends BaseFragment implements OnRefreshListener {

	private StarPublisherController controller;

	@Inject
	protected PublisherService service;

	@InjectView(R.id.fragment_start_publisher_refresh)
	protected SwipeRefreshLayout refreshView;

	@InjectView(R.id.fragment_start_publisher_list)
	protected RecyclerView recyclerView;

	private StarPublisherAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new StarPublisherAdapter();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		controller = (StarPublisherController) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_star_publisher, container, false);
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
				refreshView.setRefreshing(service.isUpdating().doSync());
			}
		}, 1000);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(listener);
		service.getAll().doAsync();
	}

	@Override
	public void onRefresh() {
		if (!service.isUpdating().doSync()) {
			service.updateAll().doAsync();
		}
	}

	private SimplePublisherListener listener = new SimplePublisherListener() {

		@Override
		public void onGathered(List<Publisher> publishers) {
			adapter.addItems(publishers);

			service.getStarred().doAsync();
		}

		@Override
		public void onUpdated(List<Publisher> publishers) {
			refreshView.setRefreshing(false);

			if (publishers != null) {
				adapter.addItems(publishers);
			}
		}

		@Override
		public void onStarred(List<Publisher> publishers) {
			for (Publisher starred : publishers) {
				adapter.selectItem(starred);
			}
		}

	};

	@Override
	public void onPause() {
		super.onPause();

		controller.star(adapter.getSelection());
		service.unregisterListener(listener);
	}

}

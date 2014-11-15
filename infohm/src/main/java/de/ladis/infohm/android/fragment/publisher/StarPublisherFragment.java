package de.ladis.infohm.android.fragment.publisher;

import java.util.List;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.publisher.StarPublisherAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.PublisherListener;
import de.ladis.infohm.core.service.PublisherService;

public class StarPublisherFragment extends BaseFragment implements PublisherListener {

	@Inject
	protected PublisherService service;

	@InjectView(R.id.fragment_start_publisher_list)
	protected RecyclerView recyclerView;

	private StarPublisherAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new StarPublisherAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_star_publisher, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(this);

		service.getAll().doAsync();
		service.updateAll().doAsync();
	}

	@Override
	public void onUpdated(List<Publisher> publishers) {
		adapter.addItems(publishers);
		service.getStarred().doAsync();
	}

	@Override
	public void onGathered(List<Publisher> publishers) {
		adapter.addItems(publishers);
		service.getStarred().doAsync();
	}

	@Override
	public void onStarred(List<Publisher> publishers) {
		for (Publisher starred : publishers) {
			adapter.selectItem(starred);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		service.unstarFromAll().doSync();
		for (Publisher starred : adapter.getSelection()) {
			service.starTo(starred).doSync();
		}

		service.unregisterListener(this);
	}

}
package de.ladis.infohm.android.fragment.events;

import java.util.List;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import butterknife.InjectView;
import butterknife.OnClick;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.publisher.StarPublisherActivity;
import de.ladis.infohm.android.adapter.events.EventsPagerAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.widget.SlidingTabLayout;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.PublisherListener;
import de.ladis.infohm.core.listener.SimplePublisherListener;
import de.ladis.infohm.core.service.PublisherService;

public class EventsPagerFragment extends BaseFragment {

	@Inject
	protected PublisherService service;

	@Inject
	protected DisplayMetrics metrics;

	@InjectView(R.id.fragment_events_pager_tabs)
	protected SlidingTabLayout tabView;

	@InjectView(R.id.fragment_events_pager)
	protected ViewPager pagerView;

	@InjectView(R.id.fragment_events_pager_action)
	protected View actionView;

	private EventsPagerAdapter adapter;
	private Boolean animate;

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

		animate = savedInstanceState == null;
	}

	@Override
	public void onResume() {
		super.onResume();

		adapter.clearItems();

		service.registerListener(listener);
		service.getStarred().doAsync();
	}

	private final PublisherListener listener = new SimplePublisherListener() {

		@Override
		public void onStarred(List<Publisher> publishers) {
			if (publishers != null) {
				adapter.addItems(publishers);

				alignActionButton();

				if (animate) {
					animateTabAppearance();
					animateActionButton();
				}
			}
		}

	};

	private void animateTabAppearance() {
		int count = adapter.getCount();

		ViewCompat.setAlpha(tabView, 0);
		ViewCompat.setTranslationY(tabView, -50 * metrics.density);
		ViewCompat.animate(tabView)
				.translationY(0)
				.alpha(1)
				.setDuration(600)
				.start();

		ViewGroup tabStrip = (ViewGroup) tabView.getChildAt(0);
		for (int i = 0; i < count; i++) {
			View viewView = tabStrip.getChildAt(i);

			ViewCompat.setAlpha(viewView, 0);
			ViewCompat.setTranslationX(viewView, 25 * metrics.density);
			ViewCompat.animate(viewView)
					.alpha(1)
					.translationX(0)
					.setStartDelay(300 * (i+1))
					.start();
		}
	}

	private void animateActionButton() {
		int count = adapter.getCount();

		if (count > 1) {
			ViewCompat.setTranslationY(actionView, 200 * metrics.density);

			ViewCompat.animate(actionView)
					.translationY(0)
					.setDuration(800)
					.start();
		}
	}

	private void alignActionButton() {
		int count = adapter.getCount();

		if (count > 1) {
			RelativeLayout.LayoutParams params = (LayoutParams) actionView.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

			actionView.setLayoutParams(params);
		}
	}

	@OnClick(R.id.fragment_events_pager_action)
	public void starPublisher(View view) {
		Context context = view.getContext();

		Intent intent = new Intent(context, StarPublisherActivity.class);
		startActivity(intent);
	}

	@Override
	public void onPause() {
		super.onPause();

		animate = false;
		service.unregisterListener(listener);
	}

}

package de.ladis.infohm.android.fragment.events;

import static android.view.View.*;
import java.util.List;

import javax.inject.Inject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import butterknife.OnClick;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.publisher.StarPublisherActivity;
import de.ladis.infohm.android.adapter.events.EventsPagerAdapter;
import de.ladis.infohm.android.animation.events.EventsPagerAnimator;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.widget.SlidingTabLayout;
import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.listener.PublisherListener;
import de.ladis.infohm.core.listener.SimplePublisherListener;
import de.ladis.infohm.core.service.PublisherService;

public class EventsPagerFragment extends BaseFragment {

	@Inject
	protected PublisherService service;

	@InjectView(R.id.fragment_events_pager_tabs)
	protected SlidingTabLayout tabView;

	@InjectView(R.id.fragment_events_pager)
	protected ViewPager pagerView;

	@InjectView(R.id.fragment_events_pager_action_overlay)
	protected View overlayView;

	private EventsPagerAdapter adapter;
	private EventsPagerAnimator animator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_events_pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		animator = new EventsPagerAnimator(this);
		animator.animateOneShots(savedInstanceState == null);

		adapter = new EventsPagerAdapter(getChildFragmentManager());

		pagerView.setAdapter(adapter);
		tabView.setViewPager(pagerView);
	}

	@Override
	public void onResume() {
		super.onResume();

		adapter.clearItems();

		overlayView.setVisibility(INVISIBLE);

		service.registerListener(listener);
		service.getStarred().doAsync();
	}

	private final PublisherListener listener = new SimplePublisherListener() {

		@Override
		public void onStarred(List<Publisher> publishers) {
			if (publishers != null) {
				adapter.addItems(publishers);

				animator.animateActionButton();
				animator.animateTabAppearance();
			}
		}

	};

	@OnClick(R.id.fragment_events_pager_action)
	protected void starPublisher(View view) {
		final Activity activity = getActivity();
		final Intent intent = new Intent(activity, StarPublisherActivity.class);

		animator.animateActionTransition(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				activity.startActivity(intent);
				if (animation != null) {
					activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}
			}

		});
	}

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

}

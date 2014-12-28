package de.ladis.infohm.android.fragment.navigation;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.controller.NavigationController;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.service.AuthenticationService;

public class NavigationFragment extends BaseFragment {

	private NavigationController controller;

	@Inject
	protected AuthenticationService service;

	@InjectView(R.id.fragment_navigation_username)
	protected TextView usernameView;

	protected View selectedView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		controller = (NavigationController) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_navigation, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Account account = service.getAccount().doSync();

		if (account != null) {
			usernameView.setText(account.name);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		setSelectionTo(controller.initialItem());
	}

	@OnClick({
			R.id.fragment_navigation_newsfeed,
			R.id.fragment_navigation_bookmarks,
			R.id.fragment_navigation_cafeteria,
			R.id.fragment_navigation_feedback,
			R.id.fragment_navigation_settings,
	})
	protected void setSelectionTo(View view) {
		setSelectionTo(view.getId());
	}

	private void setSelectionTo(int id) {
		View view = getView().findViewById(id);

		if (view != null && view != selectedView) {
			view.setActivated(true);

			if (selectedView != null) {
				selectedView.setActivated(false);
				controller.selected(id);
			}

			selectedView = view;
		}
	}

}

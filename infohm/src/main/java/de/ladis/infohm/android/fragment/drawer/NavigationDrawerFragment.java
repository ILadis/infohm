package de.ladis.infohm.android.fragment.drawer;

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
import de.ladis.infohm.android.controller.NavigationDrawerController;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.service.AuthenticationService;

public class NavigationDrawerFragment extends BaseFragment {

	private NavigationDrawerController controller;

	@Inject
	protected AuthenticationService service;

	@InjectView(R.id.fragment_navigation_drawer_username)
	protected TextView usernameView;

	protected View selectedView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		controller = (NavigationDrawerController) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (savedInstanceState != null) {
			setSelectionTo(savedInstanceState.getInt("selectedView"));
		} else {
			setSelectionTo(controller.initialItem());
		}

		Account account = service.getAccount().doSync();

		if (account != null) {
			usernameView.setText(account.name);
		}
	}

	@OnClick({
			R.id.fragment_navigation_drawer_item_newsfeed,
			R.id.fragment_navigation_drawer_item_bookmarks,
			R.id.fragment_navigation_drawer_item_cafeterian,
			R.id.fragment_navigation_drawer_item_feedback,
			R.id.fragment_navigation_drawer_item_settings,
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (selectedView != null) {
			outState.putInt("selectedView", selectedView.getId());
		}
	}

}

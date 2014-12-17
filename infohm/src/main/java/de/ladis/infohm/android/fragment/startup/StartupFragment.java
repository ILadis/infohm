package de.ladis.infohm.android.fragment.startup;

import static android.view.View.*;

import javax.inject.Inject;

import org.joda.time.DateTime;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.controller.StartupController;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.listener.SimpleSynchronizeListener;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.SynchronizeService;

public class StartupFragment extends BaseFragment {

	private StartupController controller;

	@Inject
	protected AuthenticationService authService;

	@Inject
	protected SynchronizeService syncService;

	@InjectView(R.id.fragment_startup_progress)
	protected ProgressBar progressView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		controller = (StartupController) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);

		syncService.registerListener(syncListener);

		Account account = authService.getAccount().doSync();

		authService.authenticate(account).doSync();
		syncService.lastSync(account).doAsync();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_startup, container, false);
	}

	private SimpleSynchronizeListener syncListener = new SimpleSynchronizeListener() {

		@Override
		public void onLastSync(Account account, DateTime when) {
			if (when == null) {
				progressView.setVisibility(VISIBLE);
				syncService.requestSync(account).doSync();
			} else {
				onSynced();
			}
		}

		@Override
		public void onSynced() {
			controller.startupComplete();
		}

	};

	@Override
	public void onDestroy() {
		super.onDestroy();

		syncService.unregisterListener(syncListener);
	}

}

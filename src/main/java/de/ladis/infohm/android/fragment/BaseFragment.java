package de.ladis.infohm.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.ButterKnife;
import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;

public abstract class BaseFragment extends Fragment implements Injector {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		inject(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		ButterKnife.inject(this, view);
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getActivity().getApplication();
		app.inject(object);
	}

}

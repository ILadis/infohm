package de.ladis.infohm.android.fragment;

import butterknife.ButterKnife;
import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDialogFragment extends DialogFragment implements Injector {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		inject(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.inject(view);

		return view;
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getActivity().getApplication();
		app.inject(object);
	}

}

package de.ladis.infohm.android.fragment.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.ladis.infohm.R;
import de.ladis.infohm.android.fragment.BaseFragment;

public class NavigationDrawerFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
	}

}

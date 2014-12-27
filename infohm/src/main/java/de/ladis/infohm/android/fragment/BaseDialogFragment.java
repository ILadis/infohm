package de.ladis.infohm.android.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import de.ladis.infohm.R;
import de.ladis.infohm.android.Application;
import de.ladis.infohm.util.Injector;

public abstract class BaseDialogFragment extends DialogFragment implements Injector {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		inject(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public final Dialog onCreateDialog(Bundle savedInstanceState) {
		Context context = getActivity();
		Dialog dialog = new Dialog(context, R.style.Theme_Dialog);

		return dialog;
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getActivity().getApplication();
		app.inject(object);
	}

}

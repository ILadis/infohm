package de.ladis.infohm.android.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import de.ladis.infohm.R;
import de.ladis.infohm.android.Application;
import de.ladis.infohm.android.widget.ScrollView;
import de.ladis.infohm.android.widget.ScrollView.OnScrollableListener;
import de.ladis.infohm.util.Injector;

public abstract class BaseDialogFragment extends DialogFragment implements Injector {

	private TextView titleView;
	private ScrollView contentView;
	private View dividerView;
	private Button positiveButton, negativeButton;

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
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getContext(), R.layout.fragment_dialog_base, null);

		titleView = (TextView) view.findViewById(R.id.fragment_dialog_base_title);
		contentView = (ScrollView) view.findViewById(R.id.fragment_dialog_base_content);
		dividerView = view.findViewById(R.id.fragment_dialog_base_divider);
		positiveButton = (Button) view.findViewById(R.id.fragment_dialog_base_actions_positive);
		negativeButton = (Button) view.findViewById(R.id.fragment_dialog_base_actions_negative);

		return view;
	}

	@Override
	public final void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup container = contentView;

		View dialog = onCreateDialogContentView(inflater, container, savedInstanceState);
		container.addView(dialog);

		onDialogTitleViewCreated(titleView);
		onDialogActionViewCreated(positiveButton, negativeButton);

		ButterKnife.inject(this, view);
	}

	public abstract void onDialogTitleViewCreated(TextView title);

	public abstract View onCreateDialogContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	public abstract void onDialogActionViewCreated(Button positive, Button negative);

	@Override
	public void onResume() {
		super.onResume();

		contentView.setOnScrollableListener(new OnScrollableListener() {
			@Override
			public void onCanScroll(ScrollView view, boolean scrollable) {
				dividerView.setVisibility(scrollable ? View.VISIBLE : View.GONE);
			}
		});
	}

	public final Context getContext() {
		return getDialog().getContext();
	}

	@Override
	public void inject(Object object) {
		Application app = (Application) getActivity().getApplication();
		app.inject(object);
	}

	@Override
	public void onPause() {
		super.onPause();

		contentView.setOnScrollableListener(null);
	}

}

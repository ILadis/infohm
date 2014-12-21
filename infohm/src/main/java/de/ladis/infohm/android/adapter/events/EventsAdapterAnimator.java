package de.ladis.infohm.android.adapter.events;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

public class EventsAdapterAnimator extends RecyclerView.ItemAnimator {

	private List<ViewHolder> pending = new ArrayList<RecyclerView.ViewHolder>();

	@Override
	public void runPendingAnimations() {
		for (ViewHolder holder : pending) {
			ViewCompat.setTranslationY(holder.itemView, 200);
			ViewCompat.setAlpha(holder.itemView, 0);

			ViewCompat.animate(holder.itemView)
					.translationY(0)
					.alpha(1)
					.start();
		}

		pending.clear();
	}

	@Override
	public boolean isRunning() {
		return pending.size() != 0;
	}

	@Override
	public void endAnimations() {
		pending.clear();
	}

	@Override
	public void endAnimation(ViewHolder holder) {
		pending.remove(holder);
	}

	@Override
	public boolean animateRemove(ViewHolder holder) {
		return false;
	}

	@Override
	public boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
		return false;
	}

	@Override
	public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
		return false;
	}

	@Override
	public boolean animateAdd(ViewHolder holder) {
		return pending.add(holder);
	}

}

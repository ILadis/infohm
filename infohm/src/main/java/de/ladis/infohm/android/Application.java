package de.ladis.infohm.android;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import de.ladis.infohm.android.module.AndroidModule;
import de.ladis.infohm.util.Injector;

public class Application extends android.app.Application implements Injector {

	private ObjectGraph graph;

	protected synchronized ObjectGraph getGraph() {
		if (graph == null) {
			graph = ObjectGraph.create(getModules().toArray());
		}

		return graph;
	}

	protected List<Object> getModules() {
		return Arrays.<Object>asList(
				new AndroidModule(this)
		);
	}

	@Override
	public void inject(Object object) {
		getGraph().inject(object);
	}

}

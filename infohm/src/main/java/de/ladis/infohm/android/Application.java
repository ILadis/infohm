package de.ladis.infohm.android;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import de.ladis.infohm.android.module.AndroidModule;
import de.ladis.infohm.android.module.TestModule;
import de.ladis.infohm.util.Injector;

public class Application extends android.app.Application implements Injector {

	private ObjectGraph graph;

	@Override
	public void onCreate() {
		super.onCreate();

		graph = ObjectGraph.create(getModules().toArray());
	}

	protected List<Object> getModules() {
		return Arrays.asList(
				new AndroidModule(this),
				new TestModule()
		);
	}

	@Override
	public void inject(Object object) {
		graph.inject(object);
	}

}

package de.ladis.infohm.test;

import java.util.Collections;
import java.util.List;

import org.junit.Before;

import dagger.ObjectGraph;
import de.ladis.infohm.util.Injector;

public class BaseTest implements Injector {

	private ObjectGraph graph;

	protected synchronized ObjectGraph getGraph() {
		if (graph == null) {
			graph = ObjectGraph.create(getModules().toArray());
		}

		return graph;
	}

	protected List<Object> getModules() {
		return Collections.emptyList();
	}

	@Before
	public void before() {
		inject(this);
	}

	@Override
	public void inject(Object object) {
		getGraph().inject(object);
	}

}

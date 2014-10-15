package de.ladis.infohm.core.dao;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public abstract class DaoFactory {

	private final Map<Class<? extends Dao<?, ?>>, Dao<?, ?>> cache;

	public DaoFactory() {
		this.cache = new HashMap<Class<? extends Dao<?,?>>, Dao<?,?>>();
	}

	public <T extends Dao<?, ?>> T create(Class<T> dao, Object... args) {
		return getInstanceOf(dao, args);
	}

	@SuppressWarnings("unchecked")
	protected <T extends Dao<?, ?>> T getInstanceOf(Class<T> dao, Object... args) {
		if (cache.containsKey(dao)) {
			return (T) cache.get(dao);
		}

		try {
			Class<?>[] parameters = new Class<?>[args.length];

			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = args[i].getClass();
			}

			Constructor<T> constructor = dao.getConstructor(parameters);

			T instance = constructor.newInstance(args);
			cache.put(dao, instance);

			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

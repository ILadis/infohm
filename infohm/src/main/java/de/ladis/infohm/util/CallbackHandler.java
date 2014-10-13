package de.ladis.infohm.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Handler;
import android.os.Looper;

@SuppressWarnings("unchecked")
public class CallbackHandler<T> {

	private final T proxy;
	private final Map<T, Looper> callbacks;

	public CallbackHandler(Class<T> clazz) {
		this.proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
		this.callbacks = new HashMap<T, Looper>();
	}

	public void register(T callback) {
		synchronized (callbacks) {
			callbacks.put(callback, Looper.getMainLooper());
		}
	}

	public void register(T callback, Looper looper) {
		synchronized (callbacks) {
			callbacks.put(callback, looper);
		}
	}

	public void unregister(T subscriber) {
		synchronized (callbacks) {
			callbacks.remove(subscriber);
		}
	}

	public T callback() {
		return proxy;
	}

	private final InvocationHandler handler = new InvocationHandler() {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			synchronized (callbacks) {
				Iterator<Entry<T, Looper>> iterator = callbacks.entrySet().iterator();

				while (iterator.hasNext()) {
					Entry<T, Looper> entry = iterator.next();

					Runnable callback = createCallback(entry.getKey(), method, args);
					Handler handler = new Handler(entry.getValue());

					handler.post(callback);
				}
			}

			return null;
		}

		private Runnable createCallback(final T subscriber, final Method method, final Object[] args) {
			return new Runnable() {

				@Override
				public void run() {
					try {
						method.invoke(subscriber, args);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			};
		}

	};

	public void clear() {
		callbacks.clear();
	}

}

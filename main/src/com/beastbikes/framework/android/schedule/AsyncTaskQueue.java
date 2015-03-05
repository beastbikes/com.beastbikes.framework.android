package com.beastbikes.framework.android.schedule;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.os.AsyncTask;

public class AsyncTaskQueue {

	private static final int CPU_COUNT = Runtime.getRuntime()
			.availableProcessors();

	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

	private static final int KEEP_ALIVE = 1;

	private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
		private final AtomicInteger count = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			return new Thread(r, "QueuedAsyncTask #"
					+ this.count.getAndIncrement());
		}

	};

	private final BlockingQueue<Runnable> workQueue;

	private final SerialExecutor serialExecutor;

	private final ThreadPoolExecutor poolExecutor;

	private static final class SerialExecutor implements Executor {

		private final Deque<Runnable> tasks = new LinkedList<Runnable>();

		private Runnable active;

		private final AsyncTaskQueue queue;

		public SerialExecutor(AsyncTaskQueue queue) {
			this.queue = queue;
		}

		public synchronized void execute(final Runnable r) {
			if (this.queue.poolExecutor.isShutdown())
				return;

			this.tasks.offer(new Runnable() {
				public void run() {
					try {
						r.run();
					} finally {
						scheduleNext();
					}
				}
			});
			if (this.active == null) {
				scheduleNext();
			}
		}

		protected synchronized void scheduleNext() {
			if ((this.active = this.tasks.poll()) != null) {
				this.queue.poolExecutor.execute(this.active);
			}
		}

		protected synchronized void cancelAll(Object object) {
			this.tasks.clear();
		}
	}

	AsyncTaskQueue(Context ctx) {
		this.workQueue = new LinkedBlockingQueue<Runnable>();
		this.serialExecutor = new SerialExecutor(this);
		this.poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
				MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
				this.workQueue, THREAD_FACTORY);
	}

	/**
	 * Add the specified task to this queue
	 * 
	 * @param task
	 *            An {@link AsyncTask}
	 * @param params
	 *            The parameters of the task
	 * @return
	 */
	public <Params, Progress, Result> AsyncTask<Params, Progress, Result> add(
			AsyncTask<Params, Progress, Result> task, Params... params) {
		return task.executeOnExecutor(this.serialExecutor, params);
	}

	public void cancelAll(Object object) {
		this.serialExecutor.cancelAll(object);
	}

	public void start() {
		this.poolExecutor.prestartCoreThread();
	}

	public void stop() {
		this.cancelAll(null);
		this.poolExecutor.shutdownNow();
	}

}

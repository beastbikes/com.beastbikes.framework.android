package com.beastbikes.framework.android.schedule;

import java.io.File;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class RequestQueueFactory {

	private static final String DEFAULT_CACHE_DIR = "volley";

	private RequestQueueFactory() {
	}

	public static RequestQueue newRequestQueue(Context context) {
		final Network network = new BasicNetwork(new HurlStack());
		final RequestQueue queue = new RequestQueue(getCache(context), network);
		queue.start();
		return queue;
	}

	private static Cache getCache(Context ctx) {
		final File dir = new File(ctx.getCacheDir(), DEFAULT_CACHE_DIR);
		return new DiskBasedCache(dir, Integer.MAX_VALUE);
	}

}

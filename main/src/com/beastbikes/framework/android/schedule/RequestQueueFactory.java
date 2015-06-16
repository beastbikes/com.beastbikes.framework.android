package com.beastbikes.framework.android.schedule;

import java.io.File;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.StatFs;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.beastbikes.framework.android.utils.PackageUtils;

public class RequestQueueFactory {

	private static final String DEFAULT_CACHE_DIR = "volley";

	private RequestQueueFactory() {
	}

	public static RequestQueue newRequestQueue(Context context) {
		final String userAgent = getUserAgent(context);
		final AndroidHttpClient ahc = AndroidHttpClient.newInstance(userAgent);
		final Network network = new BasicNetwork(new HttpClientStack(ahc));
		final RequestQueue queue = new RequestQueue(getCache(context), network);
		queue.start();
		return queue;
	}

	private static Cache getCache(Context ctx) {
		final File dir = new File(ctx.getCacheDir(), DEFAULT_CACHE_DIR);
		return new DiskBasedCache(dir, getCacheSize(ctx));
	}

	private static String getUserAgent(Context ctx) {
		return ctx.getPackageName() + "/" + PackageUtils.getVersionName(ctx);
	}

	@SuppressWarnings("deprecation")
	private static int getCacheSize(Context ctx) {
		final StatFs stat = new StatFs(ctx.getCacheDir().getAbsolutePath());
		return stat.getAvailableBlocks() * stat.getBlockSize();
	}

}

package com.beastbikes.framework.android.cache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.beastbikes.framework.android.schedule.RequestQueueManager;

/**
 * The cache manager
 * 
 * @author johnson
 * 
 */
public class CacheManager implements ImageCache {

	public static final File getFile(Context ctx, String... segment) {
		File file = ctx.getExternalCacheDir();

		for (int i = 0; i < segment.length; i++) {
			file = new File(file, segment[i]);
		}

		return file;
	}

	private final LruCache<String, Bitmap> images;

	private CacheManager() {
		final int cacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);
		this.images = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap bmp) {
				return bmp.getByteCount();
			}

		};
	}

	@Override
	public Bitmap getBitmap(String key) {
		return this.images.get(key);
	}

	@Override
	public void putBitmap(String key, Bitmap bmp) {
		this.images.put(key, bmp);
	}

	/**
	 * Returns an image loader
	 * 
	 * @param rqm
	 *            The request queue manager
	 * @return an image loader
	 */
	public ImageLoader getImageLoader(RequestQueueManager rqm) {
		return new ImageLoader(rqm.getRequestQueue(), this);
	}

}

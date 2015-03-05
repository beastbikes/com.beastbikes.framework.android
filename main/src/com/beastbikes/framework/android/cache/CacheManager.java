package com.beastbikes.framework.android.cache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.beastbikes.framework.android.schedule.RequestQueueManager;
import com.beastbikes.framework.android.utils.FileUtils;

/**
 * The cache manager
 * 
 * @author johnson
 * 
 */
public class CacheManager implements ImageCache {

	private static CacheManager instance = null;

	public static synchronized final CacheManager getInstance() {
		if (null == instance) {
			instance = new CacheManager();
		}

		return instance;
	}

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

	public Bitmap loadBitmapFromFile(String path) {
		final BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;
		opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inSampleSize = 2;
		final Bitmap bmp = BitmapFactory.decodeFile(path, opts);

		try {
			return bmp;
		} finally {
			this.putBitmap(path, bmp);
		}
	}

	public void clear(Context context) {
		final File root = context.getExternalCacheDir();
		final File[] files = root.listFiles();

		for (int i = 0; i < files.length; i++) {
			FileUtils.delete(files[i], true);
		}
	}

}

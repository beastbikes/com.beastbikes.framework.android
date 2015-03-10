package com.beastbikes.framework.android;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.ViewConfiguration;

/**
 * An implementation of interface {@link ApplicationContext} for Android
 * 
 * @author johnson
 * 
 */
public abstract class ApplicationContext extends Application implements
		ActivityLifecycleCallbacks {

	/**
	 * Returns the value of the specified meta data
	 * 
	 * @param key
	 *            The name of meta data
	 * @param defaultValue
	 *            The default value if the specified meta data not found
	 * @return The value of the specified meta data, if not found, the
	 *         {@code defaultValue} is returned.
	 */
	public String getMetaData(String key, String defaultValue) {
		final String pkg = getPackageName();
		final PackageManager pm = getPackageManager();

		try {
			final ApplicationInfo ai = pm.getApplicationInfo(pkg,
					PackageManager.GET_META_DATA);
			final Bundle metaData = ai.metaData;
			if (null == metaData)
				return null;

			return metaData.getString(key, defaultValue);
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public int getVersionCode() {
		final String pkg = getPackageName();
		final PackageManager pm = getPackageManager();
		try {
			return pm.getPackageInfo(pkg, 0).versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	public String getVersionName() {
		final String pkg = getPackageName();
		final PackageManager pm = getPackageManager();
		try {
			return pm.getPackageInfo(pkg, 0).versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// register activity life-cycle listener
		this.registerActivityLifecycleCallbacks(this);

		// show action overflow button in force
		final ViewConfiguration vc = ViewConfiguration.get(this);
		try {
			final Class<?> clazz = vc.getClass();
			final Field f = clazz.getDeclaredField("sHasPermanentMenuKey");
			if (f != null) {
				f.setAccessible(true);
				f.setBoolean(vc, false);
			}
		} catch (Exception e) {
			// ignore
		}

	}

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
	}

	@Override
	public void onActivityStarted(Activity activity) {
	}

	@Override
	public void onActivityResumed(Activity activity) {
	}

	@Override
	public void onActivityPaused(Activity activity) {
	}

	@Override
	public void onActivityStopped(Activity activity) {
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
	}

}

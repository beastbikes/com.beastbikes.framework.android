package com.beastbikes.framework.android.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public final class PackageUtils {

	/**
	 * Install the specified package
	 * 
	 * @param context
	 *            The android context
	 * @param file
	 *            The package file to be installed
	 */
	public static final void installPackage(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * Returns the version code
	 * 
	 * @param context
	 * @return
	 */
	public static final int getVersionCode(Context context) {
		final String pkg = context.getPackageName();
		final PackageManager pm = context.getPackageManager();

		try {
			return pm.getPackageInfo(pkg, 0).versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	/**
	 * Returns the version name
	 * 
	 * @param context
	 * @return
	 */
	public static final String getVersionName(Context context) {
		final String pkg = context.getPackageName();
		final PackageManager pm = context.getPackageManager();

		try {
			return pm.getPackageInfo(pkg, 0).versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	private PackageUtils() {
	}

}

package com.beastbikes.framework.android.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
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

	private PackageUtils() {
	}

}

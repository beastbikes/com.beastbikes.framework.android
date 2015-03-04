package com.beastbikes.framework.android.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

/**
 * Process operating utility
 * 
 * @author johnson
 * 
 */
public class ProcessUtils {

	private ProcessUtils() {
	}

	/**
	 * Returns the current process name
	 * 
	 * @param ctx
	 *            The context
	 * @return the current process name
	 */
	public static String getCurrentProcessName(Context ctx) {
		final int pid = android.os.Process.myPid();
		final ActivityManager am = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (final RunningAppProcessInfo rapi : am.getRunningAppProcesses()) {
			if (rapi.pid == pid) {
				return rapi.processName;
			}
		}

		return null;
	}

}

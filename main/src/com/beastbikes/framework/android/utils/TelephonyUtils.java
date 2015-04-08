package com.beastbikes.framework.android.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public abstract class TelephonyUtils {

	private TelephonyUtils() {
	}

	/**
	 * Returns the device id
	 * 
	 * @param context
	 * @return the device id
	 */
	public static String getDeviceId(Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getDeviceId();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the SIM serial number
	 * 
	 * @param context
	 * @return the SIM serial number
	 */
	public static String getSimSerialNumber(Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getSimSerialNumber();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns a constant indicating the state of the device SIM card.
	 * 
	 * @param context
	 * @return
	 */
	public static int getSimState(Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getSimState();
		} catch (Exception e) {
			return TelephonyManager.SIM_STATE_UNKNOWN;
		}
	}

	/**
	 * Returns a constant indicating the device phone type. This indicates the
	 * type of radio used to transmit voice calls.
	 * 
	 * @param context
	 * @return
	 */
	public static int getPhoneType(Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getPhoneType();
		} catch (Exception e) {
			return TelephonyManager.PHONE_TYPE_NONE;
		}
	}

	/**
	 * Returns the network type for current data connection
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getNetworkType();
		} catch (Exception e) {
			return TelephonyManager.NETWORK_TYPE_UNKNOWN;
		}
	}

}

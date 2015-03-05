package com.beastbikes.framework.android.preference;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class LongEditTextPreference extends EditTextPreference {

	public LongEditTextPreference(Context context) {
		this(context, null);
	}

	public LongEditTextPreference(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LongEditTextPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected String getPersistedString(String defaultReturnValue) {
		long defaultValue = 0;

		try {
			defaultValue = Long.decode(defaultReturnValue);
		} catch (NumberFormatException e) {
			defaultValue = 0;
		} catch (NullPointerException e) {
			defaultValue = 0;
		}

		return String.valueOf(getPersistedLong(defaultValue));
	}

	@Override
	protected boolean persistString(String value) {
		try {
			return persistLong(Long.decode(value));
		} catch (NumberFormatException e) {
			return false;
		}
	}

}

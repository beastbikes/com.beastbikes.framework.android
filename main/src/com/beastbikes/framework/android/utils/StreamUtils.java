package com.beastbikes.framework.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class StreamUtils {

	public static final String readAsString(InputStream is) throws IOException {
		final byte[] buf = new byte[1024 * 1024];

		ByteArrayOutputStream baos = null;

		try {
			baos = new ByteArrayOutputStream();

			for (int n = 0; -1 != (n = is.read(buf));) {
				baos.write(buf, 0, n);
			}

			baos.flush();
			return baos.toString();
		} finally {
			if (null != baos) {
				try {
					baos.close();
				} catch (IOException e) {
				}
				baos = null;
			}
		}

	}

	private StreamUtils() {
	}
}

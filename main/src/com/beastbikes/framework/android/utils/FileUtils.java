package com.beastbikes.framework.android.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * File operating utility
 * 
 * @author johnson
 * 
 */
public final class FileUtils {

	/**
	 * Copy the {@code src} to {@code dest}
	 * 
	 * @param dest
	 *            The destination file
	 * @param src
	 *            The source file
	 * @throws IOException
	 */
	public static void copy(File dest, File src) throws IOException {
		final byte[] buffer = new byte[8192];

		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			if (!dest.exists()) {
				dest.createNewFile();
			}

			fis = new FileInputStream(src);
			fos = new FileOutputStream(dest);

			for (int n = 0; (-1 != (n = fis.read(buffer)));) {
				fos.write(buffer, 0, n);
			}

			fos.flush();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
				}
				fis = null;
			}

			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
				}
				fos = null;
			}
		}
	}

	/**
	 * Delete the specified file
	 * 
	 * @param f
	 *            The file to be deleted
	 * @param recursive
	 *            The value that indicated whether delete the specified file
	 *            recursively or not if the specified file is a directory
	 * @return
	 */
	public static boolean delete(File f, boolean recursive) {
		if (!recursive || !f.isDirectory())
			return f.delete();

		boolean result = true;
		final File[] files = f.listFiles();

		for (int i = 0; i < files.length; i++) {
			result = delete(files[i], recursive) && result;
		}

		return f.delete() && result;
	}

	private FileUtils() {
	}

}

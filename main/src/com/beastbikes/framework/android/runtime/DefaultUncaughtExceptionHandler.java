package com.beastbikes.framework.android.runtime;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(DefaultUncaughtExceptionHandler.class);

	private UncaughtExceptionHandler handler;

	public DefaultUncaughtExceptionHandler() {
		this.handler = Thread.getDefaultUncaughtExceptionHandler();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable t) {
		logger.error(t.getMessage(), t);

		if (t instanceof ConnectException || t instanceof UnknownHostException) {
			return; // ignore network error
		}

		if (null == this.handler) {
			return;
		}

		this.handler.uncaughtException(thread, t);
	}

}

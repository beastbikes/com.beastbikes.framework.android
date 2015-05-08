package com.beastbikes.framework.android.webkit;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public interface RequestInterceptor {

	public WebResourceResponse intercept(WebView view, String url);

}

package uk.co.longdivision.hex.net.hackernewsapi.util;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;


public class RetryPolicyFactory {
    private static final int SOCKET_TIMEOUT_MS = 2500;
    private static final int MAX_RETRIES = 3;
    private static final int BACKOFF_MULTIPLIER = 2;

    public static RetryPolicy build() {
        return new DefaultRetryPolicy(SOCKET_TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULTIPLIER);
    }
}

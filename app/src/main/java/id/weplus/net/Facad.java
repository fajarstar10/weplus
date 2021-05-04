package id.weplus.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Facad {
    private static Facad mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    public static final int IO_TIMEOUT_ERROR = 101;

    private Facad(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized Facad getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Facad(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


    /**
     * Methode : GET
     * note : include token
     *
     * @param url           URL endpoint
     * @param callback      string response
     * @param errorCallback int response code
     */
    public void getRequestWithToken(String url, int method, final String token, final VolleyCallback callback, final VolleyErrorCallback errorCallback) {
        StringRequest strreq = new StringRequest(method, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String Response) {
                callback.onSuccessResponse(Response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                handleError(e, errorCallback);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> paramHeader = new HashMap<>();
                paramHeader.put("Content-Type", "application/json");
                paramHeader.put("Authorization","Bearer " + token);
                paramHeader.put("version","1.0.0");

//                paramHeader.put("Content-Type", "application/x-www-form-urlencoded");
                Log.i("param", paramHeader.toString());
                return paramHeader;
            }

        };

        strreq.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Facad.getInstance(mCtx).addToRequestQueue(strreq);
    }

    private void handleError(VolleyError e, final VolleyErrorCallback errorCallback) {
        System.out.println("onErrorResponse: [" + e + "]");
        if ( e instanceof TimeoutError) {
            TimeoutError te = (TimeoutError) e;
            errorCallback.onFailedResponse(IO_TIMEOUT_ERROR);
        } else if ( e instanceof  AuthFailureError ) {
            AuthFailureError auth = (AuthFailureError) e;
            errorCallback.onFailedResponse(auth.networkResponse.statusCode);
        } else if ( e instanceof NoConnectionError ) {
            NoConnectionError nce = (NoConnectionError) e;
            errorCallback.onFailedResponse(100);
        } else {
            if ( e.networkResponse != null) {
                errorCallback.onFailedResponse(e.networkResponse.statusCode);
            } else {
                errorCallback.onFailedResponse(0);
            }
        }
    }

}

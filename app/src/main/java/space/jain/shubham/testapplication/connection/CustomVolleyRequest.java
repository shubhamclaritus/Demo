package space.jain.shubham.testapplication.connection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.Map;

import space.jain.shubham.testapplication.utils.ApiList;
import space.jain.shubham.testapplication.utils.HelperMethods;


/**
 * Created by claritus on 7/3/16.
 */

public class CustomVolleyRequest {

    static CustomVolleyRequest customVolleyRequest;
    private Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private GetResultListener callback;
    private ProgressDialog dialog;

    private CustomVolleyRequest(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();

        //enable SSL Connection if required
        HttpsTrustManager.allowAllSSL();
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

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
    /**
     * create the single object of volley instance
     */
    public static synchronized CustomVolleyRequest getInstance(Context context) {
        if (customVolleyRequest == null) {
            customVolleyRequest = new CustomVolleyRequest(context);
        }

        return customVolleyRequest;
    }

    /**
     * @return the request queue object which contain the requested request queue
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.getCache().clear();
            requestQueue.start();
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * @param activity get current activity context to show the progress dialog
     */
    private void showProgressDialog(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (dialog != null ) {
                    try {
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }dialog = null;
                }

                dialog = new ProgressDialog(activity);
                dialog.setMessage("Loading...");
                //dialog.setIndeterminate(true);
                dialog.show();
                dialog.setCancelable(false);
            }
        });

    }
    /**
     * @param activity get current activity context to hide the progress dialog
     */
    private void hideProgressDialog(Activity activity) {
        if (activity != null)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (dialog != null) {
                        try {
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }dialog = null;
                    }
                }
            });
    }


    /** create a json request
     * @param activity define the requested callee
     * @param fragment define the requested callee weather from fragment
     * @param apiName Api name for which this call is init
     * @param jsonObject contain the body part
     * @param headerMap contain the header part of the request
     */
    public void setJsonRequest_POST(final Activity activity, Fragment fragment, final String apiName, JSONObject jsonObject, final HashMap<String, String> headerMap) {
        if (fragment != null) {
            this.callback = (GetResultListener) fragment;
        } else {
            this.callback = (GetResultListener) activity;
        }
        final String uri = ApiList.HOST + apiName;
        HelperMethods.LogError("url", uri);
        HelperMethods.LogError("params", jsonObject.toString());
        HelperMethods.LogError("header", headerMap.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, uri, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String data = "";
                        hideProgressDialog(activity);
                        if (response != null) {
                            data = response.toString();
                            HelperMethods.LogError("result", data);


                        }
                        callback.onTaskComplete(data, apiName, true);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                HelperMethods.LogError("error", "Error: " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                callback.onTaskComplete("error", apiName, false);
                if (networkResponse != null)
                    hideProgressDialog(activity);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    JSONObject result = null;

                    if (jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headerMap;
            }

        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(jsonObjReq);
        showProgressDialog(activity);
    }

}

package space.jain.shubham.testapplication;

import android.test.InstrumentationTestCase;

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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import space.jain.shubham.testapplication.utils.ApiList;
import space.jain.shubham.testapplication.utils.AppConstant;
import space.jain.shubham.testapplication.utils.HelperMethods;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)

public class ExampleUnitTest extends InstrumentationTestCase {
    RequestQueue mRequestQueue;

    @Before
    public void create() {

        Cache cache = new DiskBasedCache(getInstrumentation().getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
    }

    @Test
    public void testCase1() throws Exception {
        final String uri = ApiList.HOST + ApiList.Area + ApiList.AllPlaces + AppConstant.GET;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, uri, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String data = "";
                        /**
                        * check weather response is get or not
                        * */
                        assertNull(response);
                        {
                            data = response.toString();
                            HelperMethods.LogError("result", data);

                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                HelperMethods.LogError("error", "Error: " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                /**
                 * check error is get and which error is get
                 * */
                assertEquals(networkResponse.statusCode, 404);
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

                return new HashMap<>();
            }

        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjReq);

    }
    @Test
    public void testCase2() throws Exception {
        final String uri = ApiList.HOST + ApiList.Area + ApiList.AllPlaces + AppConstant.POST;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, uri, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String data = "";
                        /**
                         * check weather response is get or not
                         * */
                        assertNull(response);
                        {
                            data = response.toString();
                            HelperMethods.LogError("result", data);

                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                HelperMethods.LogError("error", "Error: " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                /**
                 * check error is get and which error is get
                 * */
                assertEquals(networkResponse.statusCode, 404);
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

                return new HashMap<>();
            }

        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjReq);

    }
}

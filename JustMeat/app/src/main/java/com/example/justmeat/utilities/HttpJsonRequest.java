package com.example.justmeat.utilities;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpJsonRequest {
    private JsonObjectRequest request;
    private RequestQueue queue;

    /***
     *
     * @param context context is always "this"
     * @param partialUrl the api code, for example "/test"
     * @param method the api method, for example Request.Method.GET
     * @param body optional json request body
     * @param onResponse what to do when server has responded
     * @param onError what to do then an error occurs
     *
     */
    public HttpJsonRequest(Context context, String partialUrl, int method, JSONObject body, final String token, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        request = new JsonObjectRequest(method, Constants.CONNECTION_STRING + partialUrl, body, onResponse, onError) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json");

                if(token != null) {
                    params.put("Authorization", ("Bearer " + token));
                }
                return params;
            }
        };
        queue = Volley.newRequestQueue(context);
    }

    /***
     *
     * @param context context is always "this"
     * @param partialUrl the api code, for example "/test"
     * @param method the api method, for example Request.Method.GET
     * @param onResponse what to do when server has responded
     * @param onError what to do then an error occurs
     */
    public HttpJsonRequest(Context context, String partialUrl, int method, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        this(context, partialUrl, method, null, null, onResponse, onError);
    }

    public HttpJsonRequest(Context context, String partialUrl, int method, JSONObject body, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        this(context, partialUrl, method, body, null, onResponse, onError);
    }

    public HttpJsonRequest(Context context, String partialUrl, int method, String token, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        this(context, partialUrl, method, null, token, onResponse, onError);
    }

    public void run() {
        queue.add(request);
    }
}



/*  Usage example
    try {
        JSONObject body = new JSONObject();
        body.put("test", "Hello");

        new HttpJsonRequest(this, "/test", Request.Method.POST, body,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Do something within context
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // An error occurred
                }
            }
        ).run();
    } catch(JSONException ex) {
       return;
    }
*/
package com.sahil4.news4u;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mySingletonInstance;
    private static Context context;
    private RequestQueue requestQueue;

    private MySingleton(Context c) {
        context = c;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mySingletonInstance == null) {
            mySingletonInstance = new MySingleton(context);
        }
        return mySingletonInstance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
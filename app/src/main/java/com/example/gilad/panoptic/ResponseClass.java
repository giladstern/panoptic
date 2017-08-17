package com.example.gilad.panoptic;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Raaz on 12/08/2017.
 */

public class ResponseClass {
    String data;

    public String getData() {
        return data;
    }

    public void setGreetings(String data) {

        this.data = data;
    }

    public ResponseClass(String data) {

        this.data = data;
    }

    public ResponseClass() {
    }
}

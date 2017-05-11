package com.example.jo.android_gps_db.DbConnection;

import com.example.jo.android_gps_db.ListView.Gpsdata_Item;
import com.example.jo.android_gps_db.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jo on 2017-04-29.
 */

public class JsonGpsData {
    private ArrayList<Gpsdata_Item> arrayList = new ArrayList<Gpsdata_Item>();

    public ArrayList<Gpsdata_Item> getArrayList(String result) throws ExecutionException, InterruptedException, JSONException {
       // String result = new CustomTask().execute("", "", "", "", "selectAll").get();

        JSONObject json = new JSONObject(result);
        JSONArray jArr = json.getJSONArray("sendData");
        for (int i = 0; i < jArr.length(); i++) {
            json = jArr.getJSONObject(i);
            if (json != null) {
                Gpsdata_Item item = new Gpsdata_Item(json.getString("latitude"), json.getString("longitude"), json.getString("radius"), json.getString("name"), json.getString("id_num"));
                item.setImage(R.drawable.ic_cloud);
                arrayList.add(item);
            }
        }
        return arrayList;
    }
}

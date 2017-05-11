package com.example.jo.android_gps_db.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jo.android_gps_db.DbConnection.CustomTask;
import com.example.jo.android_gps_db.ListView.GpsAdapter;
import com.example.jo.android_gps_db.ListView.Gpsdata_Item;
import com.example.jo.android_gps_db.Location.LocationPermission;
import com.example.jo.android_gps_db.R;

/**
 * Created by jo on 2017-04-05.
 */

public class Search extends AppCompatActivity {
    Toast toast;
    EditText searchEdit;
    Button searchBtn;
    ListView searchList;

    GpsAdapter adapter;

    //키보드 내리기
    InputMethodManager imm;
    LocationPermission locationPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        toast=Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        locationPermission = new LocationPermission(this);

        searchEdit = (EditText) findViewById(R.id.searchEdit);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchList = (ListView) findViewById(R.id.searchList);
        searchBtn.setOnClickListener(btnListener);
        adapter = new GpsAdapter(this);

        searchList.setAdapter(adapter);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Gpsdata_Item item = (Gpsdata_Item) adapter.getItem(position);

                searchEdit.setText(String.valueOf(item.getName()));

                toast.setText("선택 : " + item.getName());
                toast.show();

                //   Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        searchList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Gpsdata_Item item = (Gpsdata_Item) adapter.getItem(position);
                String delLatitude = String.valueOf(item.getLatitude());
                String delLongitude = String.valueOf(item.getLongitude());
                String delRadius = String.valueOf(item.getRadius());
                String delname = String.valueOf(item.getName());

                try {
                    String result = new CustomTask().execute(delLatitude, delLongitude, delRadius, delname, "delete").get();
                    if (result.equals("delete")) {
                        toast.setText("삭제 되었습니다.");
                        toast.show();
                        //Toast.makeText(MainActivity.this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else if (result.equals("noId")) {
                        toast.setText("위도와 경도 확인바랍니다.");
                        toast.show();
                        // Toast.makeText(MainActivity.this, "위도와 경도 확인바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                adapter.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    public void searchlinearOnclick(View v) {  //키보드 내리기 리스너
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.searchBtn:
                    String searchName = searchEdit.getText().toString();
                    try {
                        String result = new CustomTask().execute("", "", "", searchName, "search").get();
                            adapter.buildArrayList(result);
                            adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                    break;
            }
        }
    };
}

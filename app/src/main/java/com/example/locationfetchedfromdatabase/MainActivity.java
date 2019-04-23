package com.example.locationfetchedfromdatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    /*SupportMapFragment mapFragment;
    GoogleMap mMap;
    LatLng origin = new LatLng(30.739834, 76.782702);
    LatLng dest = new LatLng(30.705493, 76.801256);
    ProgressDialog progressDialog;*/

    TextView Longitude;
    TextView Latitude;
    ListView lv;
    Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Latitude    = (TextView)findViewById(R.id.latitude);
        Longitude   = (TextView)findViewById(R.id.longittude);
//        lv    = (ListView)findViewById(R.id.latitude);

        map =findViewById(R.id.button);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

//                Toast.makeText(MainActivity.this, " refreshing...", Toast.LENGTH_SHORT).show();
                final Api api = retrofit.create(Api.class);

                Call<msg> calling = api.getHeroes();

                calling.enqueue(new Callback<msg>() {
                    @Override
                    public void onResponse(Call<msg> call, final Response<msg> response) {

                        final msg data = response.body();

                        ArrayList<msg.msg_info> hero = data.getMsg();

                        final String lat[]   = new String[hero.size()];

                        for (int i = 0; i < hero.size(); i++) {
                            lat[i] =     hero.get(i).getLatitude();

                        }

                        int j =0;
                        String lng[] =new String[hero.size()];
                        for (j=0 ; j <hero.size();j++)
                        {
                            lng[j] =hero.get(j).getLongitude();

                        }


//                ArrayAdapter a = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,lat);
                        Latitude.setText(lat[0]);
                        final CharSequence a = Latitude.getText();
                        Longitude.setText(lng[0]);
                        final CharSequence b = Longitude.getText();

                        Log.e("DataBase Location", (String) a+" "+b);

//                lv.setAdapter(a);
                        map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                                        intent.putExtra("lat",a);
                                        intent.putExtra("lng",b);
                                        startActivity(intent);

                                        Log.e("latlng to activity", String.valueOf(a)+" "+b);



                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<msg> call, Throwable t) {
                        Log.d("message", String.valueOf(t));
                    }
                });
                    handler.postDelayed(this,6000);//60 second delay
            }
        };handler.postDelayed(runnable,1000);







    }




}
package com.example.a1213.ui.FindMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1213.DogInfo;
import com.example.a1213.InfoDialog;
import com.example.a1213.MainBoardActivity;
import com.example.a1213.R;
import com.example.a1213.doWalkActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FindMapActivity extends AppCompatActivity implements AutoPermissionsListener,GoogleMap.OnMarkerClickListener {
    SupportMapFragment mapFragment;
    GoogleMap map;
    EditText edId,edPw;
    TextView tv;
    Button btnFind,btnList;
    double lat,lng;
    private boolean isCameraAnimated = false;
    MarkerOptions myLocationMarker;
    MarkerOptions dogMarker=null;
    List<MarkerOptions> markerOptions;
    ArrayList<DogInfo> dogInfoList=new ArrayList<DogInfo>();
    ArrayList<DogInfo> dogInfoListFriends=new ArrayList<DogInfo>();
    ArrayList<DogInfo> dogInfoListBlack=new ArrayList<DogInfo>();

    boolean isPageOpen = false;
    Animation translateLeft;
    Animation translateRight;
    ArrayList<String> items;

    Button btnAll,btnSelect,btnBlack;

    LinearLayout page;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findmap);
        tv=findViewById(R.id.textView);
        btnFind=findViewById(R.id.btnFind);
        btnList=findViewById(R.id.btnList);
        btnAll=findViewById(R.id.btnListAll);
        btnSelect=findViewById(R.id.btnListSelect);
        btnBlack=findViewById(R.id.btnListBlack);

        listView = (ListView)this.findViewById(R.id.listView);




        page=findViewById(R.id.page);
        translateLeft= AnimationUtils.loadAnimation(this, R.anim.left_translate);
        translateRight=AnimationUtils.loadAnimation(this,R.anim.right_translate);
        PageAnimationListener listener=new PageAnimationListener();
        translateRight.setAnimationListener(listener);
        translateLeft.setAnimationListener(listener);



        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPageOpen){
                    page.startAnimation(translateRight);

                }else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeft);
                }
            }
        });
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("Map","지도 준비됨");
                map=googleMap;


            }

        });

        try{
            MapsInitializer.initialize(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLocationService();
                tv.setText("위도:"+lat+" ,경도:"+lng);
                String latStr=Double.toString(lat);
                String lngStr=Double.toString(lng);
                String km="2";
                RegisterTask task=new RegisterTask();
                task.execute(latStr,lngStr,km);


            }
        });
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),0,dogInfoList);
                listView.setAdapter(adapter);
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),0,dogInfoListFriends);
                listView.setAdapter(adapter);
            }
        });

        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),0,dogInfoListBlack);
                listView.setAdapter(adapter);

            }
        });

        AutoPermissions.Companion.loadAllPermissions(this,101);


    }

    private class CustomAdapter extends ArrayAdapter<DogInfo>{
        private ArrayList<DogInfo> infoList;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<DogInfo> objects) {
            super(context, textViewResourceId, objects);
            this.infoList = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listviewitem, null);
            }
            final int i=position;
            ImageView imageView = (ImageView) v.findViewById(R.id.ListImage);
            TextView listViewDogName= (TextView) v.findViewById(R.id.listTextName);
            TextView listViewDogKinds= (TextView) v.findViewById(R.id.listTextKinds);
            TextView listViewRS=(TextView) v.findViewById(R.id.listTextRS);
            imageView.setBackgroundResource(infoList.get(position).getDogImage());
            listViewDogName.setText(infoList.get(position).getDogName());
            listViewDogKinds.setText(infoList.get(position).getDogKinds());
            listViewRS.setText(infoList.get(position).getRelationship());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InfoDialog infoDialog=new InfoDialog(FindMapActivity.this);
                    infoDialog.callFunction(infoList.get(i).getDogNo(),infoList.get(i).getDogImage(),infoList.get(i).getDogName(),infoList.get(i).getDogKinds(),infoList.get(i).getRelationship(),infoList.get(i).getDogInfo());
                }
            });

            //final String text = items.get(position);
            return v;
        }


    }

    private class PageAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isPageOpen){
                page.setVisibility(View.INVISIBLE);
                //btnList.setText("Open");
                isPageOpen=false;
            }else{
                //btnList.setText("Close");
                isPageOpen=true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    public void onAddMarker(){
        int height = 60;
        int width = 35;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.mapmarker);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker=Bitmap.createScaledBitmap(b,width,height,false);
        for(int i=0;i<dogInfoList.size();i++) {
            LatLng latLng=new LatLng(dogInfoList.get(i).getLat(),dogInfoList.get(i).getLng());
            MarkerOptions mymarker = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    .title(Integer.toString(dogInfoList.get(i).getDogNo()))
                    .position(latLng);
            this.map.addMarker(mymarker);
        }
        this.map.setOnMarkerClickListener(markerClickListener);
    }
    GoogleMap.OnMarkerClickListener markerClickListener =new GoogleMap.OnMarkerClickListener(){

        @Override
        public boolean onMarkerClick(Marker marker) {
            DogInfo dInfo=null;
            String str="false";
            for(int i=0;i<dogInfoList.size();i++){
                if(marker.getTitle().equals(Integer.toString(dogInfoList.get(i).getDogNo()))){
                    dInfo=new DogInfo();
                    dInfo.setDogNo(dogInfoList.get(i).getDogNo());
                    dInfo.setDogName(dogInfoList.get(i).getDogName());
                    dInfo.setDogInfo(dogInfoList.get(i).getDogInfo());
                    dInfo.setDogKinds(dogInfoList.get(i).getDogKinds());
                    dInfo.setRelationship(dogInfoList.get(i).getRelationship());
                    dInfo.setDogImage(dogInfoList.get(i).getDogImage());
                }
            }
            InfoDialog infoDialog=new InfoDialog(FindMapActivity.this);
            infoDialog.callFunction(dInfo.getDogNo(),dInfo.getDogImage(),dInfo.getDogName(),dInfo.getDogKinds(),dInfo.getRelationship(),dInfo.getDogInfo());
            tv.setText(dInfo.getDogName());

            return false;
        }
    };

    LocationManager manager;
    FindMapActivity.GPSListener gpsListener = new FindMapActivity.GPSListener();

    @Override
    protected void onPause() {
        super.onPause();

    }
    public void startLocationService(){
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                lat=location.getLatitude();
                lng=location.getLongitude();
                String message = "최근 위치 -> Latitude : " + lat + "\nLongitude:" + lng;

                Log.d("Map", message);
            }
            long minTime = 30000;
            float minDistance = 0;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,gpsListener);

        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        tv.setText(dogMarker.getTitle());
        return false;
    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "내 위치 -> Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.d("Map", message);

            showCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

    private void showCurrentLocation(double lat, double lng){
        LatLng curPoint = new LatLng(lat,lng);
        LatLng dogPoint = null;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));
        showMyLocationMarker(curPoint);


    }

    private void showMyLocationMarker(LatLng curPoint){
        map.clear();
        String latStr=Double.toString(curPoint.latitude);
        String lngStr=Double.toString(curPoint.longitude);
        String km="1";
        RegisterTask task=new RegisterTask();
        task.execute(latStr,lngStr,km);
        if(myLocationMarker==null){
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(curPoint);
            myLocationMarker.title("내위치\n");
            myLocationMarker.snippet("● GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation1));
            map.addMarker(myLocationMarker);


        }else{
            myLocationMarker.position(curPoint);
            map.addMarker(myLocationMarker);
        }
    }



    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "permissions denied : " + strings.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "permissions granted : " + strings.length, Toast.LENGTH_LONG).show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    class RegisterTask extends AsyncTask<String,String,String> {

        String sendMsg, receiverMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                String str="";
                URL url=new URL("http://192.168.0.42:8092/AndroidPro/list.do");
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sendMsg="lat="+strings[0]+"&lng="+strings[1]+"&km="+strings[2];
                osw.write(sendMsg);
                osw.flush();
                osw.close();
                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStreamReader isr=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader reader=new BufferedReader(isr);
                    StringBuffer buffer=new StringBuffer();
                    while((str=reader.readLine())!=null){
                        buffer.append(str);
                    }
                    receiverMsg=buffer.toString();
                }
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            return receiverMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dogInfoList.clear();
            dogInfoListFriends.clear();
            dogInfoListBlack.clear();
            try {
                JSONArray jsonArray=new JSONArray(s);
                for(int i=0; i<jsonArray.length(); i++){
                    DogInfo dogInfo=new DogInfo();
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    dogInfo.setDogNo(jsonObject.getInt("dogNo"));
                    dogInfo.setDogName(jsonObject.getString("dogName"));
                    dogInfo.setDogInfo(jsonObject.getString("dogInfo"));
                    dogInfo.setDogKinds(jsonObject.getString("dogKinds"));
                    dogInfo.setRelationship(jsonObject.getString("relationship"));
                    dogInfo.setLat(jsonObject.getDouble("lat"));
                    dogInfo.setLng(jsonObject.getDouble("lng"));
                    dogInfo.setDistance( jsonObject.getDouble("distance"));
                    dogInfoList.add(dogInfo);
                    if(dogInfo.getRelationship().equals("1")){
                        dogInfoListFriends.add(dogInfo);
                    }else if(dogInfo.getRelationship().equals("2")){
                        dogInfoListBlack.add(dogInfo);
                    }else{

                    }




                }
                if(dogInfoList.size()>0) {
                    dogInfoList.get(0).setDogImage(R.drawable.dog0);
                    dogInfoList.get(1).setDogImage(R.drawable.dog1);
                    dogInfoList.get(2).setDogImage(R.drawable.dog2);
                    dogInfoList.get(3).setDogImage(R.drawable.dog3);
                    dogInfoList.get(4).setDogImage(R.drawable.dog4);
                    for (int i = 5; i < 20; i++) {
                        if (dogInfoList.size() > i) {
                            dogInfoList.get(i).setDogImage(R.drawable.dog10);
                        } else {
                            break;
                        }
                    }
                }


                onAddMarker();
                //tv.setText(dogInfoList.get(4).getDogName());

//                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),0,dogInfoList);
//
//                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public void onClick_Find(View view) {
        onPause();
        manager.removeUpdates(gpsListener);
        Intent intent = new Intent(this, FindMapActivity.class);
        startActivity(intent);
    }
    public void onClick_Dowalk(View view) {
        onPause();
        manager.removeUpdates(gpsListener);
        Intent intent = new Intent(this, doWalkActivity.class);
        startActivity(intent);
    }
    public void onClick_Board(View view) {
        onPause();
        manager.removeUpdates(gpsListener);
        Intent intent = new Intent(this, MainBoardActivity.class);
        startActivity(intent);
    }
}

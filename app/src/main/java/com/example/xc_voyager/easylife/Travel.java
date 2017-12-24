package com.example.xc_voyager.easylife;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Travel extends AppCompatActivity implements
        View.OnClickListener, BaiduMap.OnMapClickListener,
        OnGetRoutePlanResultListener
{
    private MapView mapView = null;
    private BaiduMap baiduMap;
    private Button buttonWalk, buttonBus, buttonDrive;
    private EditText startCity, startPlace;
    private EditText destCity, destPlace;
    private RoutePlanSearch mSearch;
    private PlanNode start, destination;
    private RouteLine route;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_travel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapView = (MapView) findViewById(R.id.bmapView);
        buttonWalk = (Button) findViewById(R.id.btn_walk);
        buttonBus = (Button) findViewById(R.id.btn_bus);
        buttonDrive = (Button) findViewById(R.id.btn_car);
        startCity = (EditText) findViewById(R.id.startCity);
        startPlace = (EditText) findViewById(R.id.startAddress);
        destCity = (EditText) findViewById(R.id.destCity);
        destPlace = (EditText) findViewById(R.id.destAddress);
        baiduMap = mapView.getMap();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback()
                {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap)
                    {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.CHINA);
                        String fname = ChartToImage.path2 + simpleDateFormat.format(new Date()) + ".png";
                        try
                        {
                            FileOutputStream out = new FileOutputStream(fname);
                            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out))
                            {
                                out.flush();
                                out.close();
                            }
                            Toast.makeText(Travel.this,"Screenshot saved!"+fname,Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            Toast.makeText(Travel.this,"Screenshot failed!"+fname,Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        });



        buttonBus.setOnClickListener(this);
        buttonDrive.setOnClickListener(this);
        buttonWalk.setOnClickListener(this);

        baiduMap.setOnMapClickListener(this);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_bus:
                setBus();
                break;
            case R.id.btn_car:
                setDrive();
                break;
            case R.id.btn_walk:
                setWalk();
                break;
        }
    }

    private void setWalk()
    {
        baiduMap.clear();
        start = PlanNode.withCityNameAndPlaceName(startCity.getText().toString(), startPlace.getText().toString());
        destination = PlanNode.withCityNameAndPlaceName(destCity.getText().toString(), destPlace.getText().toString());
        if (startCity.getText().toString().isEmpty() ||
                startPlace.getText().toString().isEmpty() ||
                destCity.getText().toString().isEmpty() ||
                destPlace.getText().toString().isEmpty())
        {
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "Start and destination can't be empty!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        }
        else
        {
            mSearch.walkingSearch(new WalkingRoutePlanOption().from(start).to(destination));
        }
    }

    private void setDrive()
    {
        baiduMap.clear();
        start = PlanNode.withCityNameAndPlaceName(startCity.getText().toString(), startPlace.getText().toString());
        destination = PlanNode.withCityNameAndPlaceName(destCity.getText().toString(), destPlace.getText().toString());
        if (startCity.getText().toString().isEmpty() ||
                startPlace.getText().toString().isEmpty() ||
                destCity.getText().toString().isEmpty() ||
                destPlace.getText().toString().isEmpty())
        {
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "Start and destination can't be empty!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        }
        else
        {
            mSearch.drivingSearch(new DrivingRoutePlanOption().from(start).to(destination));
        }
    }

    private void setBus()
    {
        baiduMap.clear();
        start = PlanNode.withCityNameAndPlaceName(startCity.getText().toString(), startPlace.getText().toString());
        destination = PlanNode.withCityNameAndPlaceName(destCity.getText().toString(), destPlace.getText().toString());
        if (startCity.getText().toString().isEmpty() ||
                startPlace.getText().toString().isEmpty() ||
                destCity.getText().toString().isEmpty() ||
                destPlace.getText().toString().isEmpty())
        {
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "Start and destination can't be empty!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        }
        else
        {
            if (startCity.getText().toString().equals(destCity.getText().toString()))
                mSearch.transitSearch(new TransitRoutePlanOption()
                        .from(start).city(startCity.getText().toString()).to(destination));
            else
                mSearch.masstransitSearch(new MassTransitRoutePlanOption().from(start).to(destination));
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result)
    {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "No result!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
        {
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "Ambiguous address!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        }
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR)
        {
            route = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result)
    {
        final DrivingRouteResult currentResult;
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "No result!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
        {
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "Ambiguous address!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        }
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR)
            if (result.getRouteLines().size() == 1)
            {
                route = result.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
            else if (result.getRouteLines().size() > 1)
            {
                currentResult = result;
                TransitDialog transitDialog = new TransitDialog(
                        Travel.this, result.getRouteLines(), RouteLineAdapter.Type.DRIVING_ROUTE
                );
                transitDialog.setOnItemInDlgClickLinster(new OnItemInDlgClickListener()
                {
                    @Override
                    public void onItemClick(int position)
                    {
                        route = currentResult.getRouteLines().get(position);
                        DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                        baiduMap.setOnMarkerClickListener(overlay);
                        overlay.setData(currentResult.getRouteLines().get(position));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }
                });
                transitDialog.show();
            }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result)
    {
        final TransitRouteResult currentResult;
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "No result!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
        {
            new AlertDialog.Builder(Travel.this)
                    .setTitle("Warning").setCancelable(true).setMessage(
                    "Ambiguous address!\n"
            ).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            }).show();
        }
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR)
            if (result.getRouteLines().size() == 1)
            {
                route = result.getRouteLines().get(0);
                TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
            else if (result.getRouteLines().size() > 1)
            {
                currentResult = result;
                TransitDialog transitDialog = new TransitDialog(
                        Travel.this, result.getRouteLines(), RouteLineAdapter.Type.TRANSIT_ROUTE
                );
                transitDialog.setOnItemInDlgClickLinster(new OnItemInDlgClickListener()
                {
                    @Override
                    public void onItemClick(int position)
                    {
                        route = currentResult.getRouteLines().get(position);
                        TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
                        baiduMap.setOnMarkerClickListener(overlay);
                        overlay.setData(currentResult.getRouteLines().get(position));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }
                });
                transitDialog.show();
            }
    }


    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult result)
    {
        new AlertDialog.Builder(Travel.this)
                .setTitle("Warning").setCancelable(true).setMessage(
                "Intercity bus option is temporarily unavailable.\n"
        ).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        }).show();
    }

    @Override
    public void onMapClick(LatLng latLng)
    {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result)
    {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult result)
    {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi)
    {
        return false;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    interface OnItemInDlgClickListener
    {
        public void onItemClick(int position);
    }

    class TransitDialog extends Dialog
    {
        private List<? extends RouteLine> mRouteLines;
        private ListView transitRouteList;
        private RouteLineAdapter transitAdapter;

        OnItemInDlgClickListener onItemInDlgClickListener;

        public TransitDialog(Context context, int theme)
        {
            super(context, theme);
        }

        public TransitDialog(Context context, List<? extends RouteLine> routeLines, RouteLineAdapter.Type type)
        {
            this(context, 0);
            mRouteLines = routeLines;
            transitAdapter = new RouteLineAdapter(context, mRouteLines, type);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_travel_transit_dialog);
            transitRouteList = (ListView) findViewById(R.id.transitList);
            transitRouteList.setAdapter(transitAdapter);
            transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    onItemInDlgClickListener.onItemClick(position);
                    dismiss();
                }
            });
        }

        public void setOnItemInDlgClickLinster(OnItemInDlgClickListener itemListener)
        {
            onItemInDlgClickListener = itemListener;
        }
    }
}

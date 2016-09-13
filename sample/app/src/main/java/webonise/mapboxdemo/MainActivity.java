package webonise.mapboxdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.dronetransects.Point;
import com.dronetransects.Transects;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final double ZOOM_DEFAULT = 14.0f;
    private MapboxMap mapboxMap;
    private MapView mapview;
    private boolean hasInternetPermission;
    private boolean hasAccessNetworkStatePermission;
    private boolean hasAccessCoarseLocationPermission;
    private boolean hasAccessFineLocationPermission;
    private List<Point> polygonPoints;
    private Polyline transectPolyline;
    private Marker firstWaypointMarker;

    //Square polygon
   /*  final List<LatLng> latLngPolygon = new ArrayList<>();

   {
        latLngPolygon.add(new LatLng(18.522198905982776, 73.77668023109436));
        latLngPolygon.add(new LatLng(18.5222294252479, 73.77941608428954));
        latLngPolygon.add(new LatLng(18.520316874109714, 73.77942681312561));
        latLngPolygon.add(new LatLng(18.520296527706048, 73.7766695022583));
        latLngPolygon.add(new LatLng(18.522198905982776, 73.77668023109436));

    }*/
/*

    final List<LatLng> latLngPolygon = new ArrayList<>();

    {
        latLngPolygon.add(new LatLng(18.5222294252479, 73.77664268016815));
        latLngPolygon.add(new LatLng(18.522987318585017, 73.77766728401184));
        latLngPolygon.add(new LatLng(18.522977145542317, 73.77920687198639));
        latLngPolygon.add(new LatLng(18.52205139612452, 73.77998471260071));
        latLngPolygon.add(new LatLng(18.52091709192927, 73.77995252609253));
        latLngPolygon.add(new LatLng(18.520316874109714, 73.77870798110962));
        latLngPolygon.add(new LatLng(18.520454212271208, 73.77709329128265));
        latLngPolygon.add(new LatLng(18.5222294252479, 73.77664268016815));
    }
*/


    final List<LatLng> latLngPolygon = new ArrayList<>();
    {
        latLngPolygon.add(new LatLng(18.517458180674886, 73.77362251281738));
        latLngPolygon.add(new LatLng(18.51379572782087, 73.77422332763672));
        latLngPolygon.add(new LatLng(18.51656292166201, 73.77563953399658));
        latLngPolygon.add(new LatLng(18.513999199480704, 73.7783432006836));
        latLngPolygon.add(new LatLng(18.51855690124174, 73.77731323242188));
        latLngPolygon.add(new LatLng(18.517458180674886, 73.77362251281738));
    }


    /*final List<LatLng> latLngPolygon = new ArrayList<>();

    {
        latLngPolygon.add(new LatLng(28.6139, 77.2090));//delhi
        latLngPolygon.add(new LatLng(22.2587, 71.1924));//gujarat
        latLngPolygon.add(new LatLng(12.9716, 77.5946));//banglore
        latLngPolygon.add(new LatLng(25.5941, 85.1376));//patna
        latLngPolygon.add(new LatLng(28.6139, 77.2090)
        );//delhi
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestForPermissions();
        initializeMapView(savedInstanceState);
        SeekBar sbRotation = (SeekBar) findViewById(R.id.sbRotation);
        sbRotation.setOnSeekBarChangeListener(onTransectRotationListener);
        mapview.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                initMapBox(mapboxMap);
            }
        });
    }

    /**
     * Function to initialize mapbox
     *
     * @param mapboxMap MapboxMap
     */
    public void initMapBox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        Toast.makeText(MainActivity.this, "Map box object initialized", Toast.LENGTH_SHORT).show();
    }

    private void requestForPermissions() {
        final PermissionUtil permissionUtil = new PermissionUtil(this);
        permissionUtil.checkPermission(Manifest.permission.INTERNET, new PermissionUtil.OnPermissionGranted() {
            @Override
            public void permissionGranted() {
                hasInternetPermission = true;
            }
        }, "Need INTERNET permission.");
        permissionUtil.checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, new PermissionUtil.OnPermissionGranted() {
            @Override
            public void permissionGranted() {
                hasAccessNetworkStatePermission = true;
            }
        }, "Need ACCESS_NETWORK_STATE permission.");
        permissionUtil.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, new PermissionUtil.OnPermissionGranted() {
            @Override
            public void permissionGranted() {
                hasAccessCoarseLocationPermission = true;
            }
        }, "Need ACCESS_COARSE_LOCATION permission.");
        permissionUtil.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, new PermissionUtil.OnPermissionGranted() {
            @Override
            public void permissionGranted() {
                hasAccessFineLocationPermission = true;
            }
        }, "Need ACCESS_FINE_LOCATION permission.");
    }

    /**
     * Function to initialize mapbox view
     *
     * @param savedInstanceState
     */
    private void initializeMapView(Bundle savedInstanceState) {
        if (hasInternetPermission) {
            mapview = (MapView) findViewById(R.id.mapview);
            mapview.onCreate(savedInstanceState);
            mapview.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    CameraPosition.Builder b = new CameraPosition.Builder();
                    b.target(latLngPolygon.get(0));
                    b.zoom(ZOOM_DEFAULT);
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(b.build()), 1000);
                    // Customize map with markers, polylines, etc.
                }
            });
        } else {
            requestForPermissions();
        }
    }

    /**
     * Function to create a polygon on the map
     *
     * @param color
     */
    private void createPolygon(LatLng[] points, int color) {
        mapboxMap.addPolyline(new PolylineOptions()
                .add(points)
                .width(2)
                .color(color));
    }

    /**
     * * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * * ++++++++++++++++++++++++++ TRANSECTS CODE +++++++++++++++++++++++++++++++++++++++++++++++
     * * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    public void onClickTransects(View view) {
        LatLng[] points = latLngPolygon.toArray(new LatLng[latLngPolygon.size()]);
        createPolygon(points, getResources().getColor(R.color.colorAccent));
        polygonPoints = new ArrayList<>();
        for (int i = 0; i < latLngPolygon.size(); i++) {
            Point point = new Point(latLngPolygon.get(i).getLatitude(),
                    latLngPolygon.get(i).getLongitude());
            polygonPoints.add(point);
        }
        generateTransects(0);
    }

    /**
     * Function to generate transects based on given angle.
     *
     * @param angle int
     */
    private void generateTransects(final int angle) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Point> waypoints = Transects.generateTransects(polygonPoints, angle, 0
                    .0003);
                    List<LatLng> latLngList = new ArrayList<>();
                    for (int i = 0; i < waypoints.size(); i++) {
                        LatLng latLng = new LatLng(waypoints.get(i).getX(),
                                waypoints.get(i).getY());
                        latLngList.add(latLng);
                    }
                    //Draw buffer polygon
                    LatLng[] waypointArray = latLngList.toArray(new LatLng[latLngList.size()]);
                    createTransects(waypointArray, getResources().getColor(R.color.mapbox_blue));
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SeekBar.OnSeekBarChangeListener onTransectRotationListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress % 3 == 0)
                generateTransects(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    /**
     * Function to create a polygon on the map
     *
     * @param color
     */
    private void createTransects(LatLng[] points, int color) {
        if (transectPolyline != null) {
            mapboxMap.removePolyline(transectPolyline);
        }
        transectPolyline = mapboxMap.addPolyline(new PolylineOptions()
                .add(points)
                .width(2)
                .color(color));
    }
}

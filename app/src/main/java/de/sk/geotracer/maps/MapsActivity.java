package de.sk.geotracer.maps;

import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import de.sk.geotracer.LocationListener;
import de.sk.geotracer.R;
import de.sk.geotracer.data.GlobalDataStore;
import de.sk.geotracer.data.Journey;
import de.sk.geotracer.data.Trip;
import de.sk.geotracer.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient locationProvider;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationProvider = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(11));
        LocationRequest request = new LocationRequest.Builder(10000)
                .setGranularity(Granularity.GRANULARITY_FINE).setPriority(Priority.PRIORITY_HIGH_ACCURACY).build();
        listener = new LocationListener(googleMap);
        locationProvider.requestLocationUpdates(request, listener, Looper.getMainLooper());
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Trip trip = listener.getTrip();
            Journey journey = GlobalDataStore.journey;
            journey.addTrip(trip);
        }
        return true;
    }
}
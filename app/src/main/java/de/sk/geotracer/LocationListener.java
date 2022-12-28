package de.sk.geotracer;

import android.graphics.Color;
import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class LocationListener extends LocationCallback {

    GoogleMap map;
    Polyline route;

    public LocationListener(GoogleMap map) {
        this.map = map;
    }

    @Override
    public void onLocationResult(@NonNull LocationResult locationResult) {
        super.onLocationResult(locationResult);

        LatLng position = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLng(position));
        if (route != null) {
            List<LatLng> points = route.getPoints();
            points.add(position);
            route.setPoints(points);
        } else {
            route = map.addPolyline(new PolylineOptions().add(position).color(Color.BLUE));
        }
    }
}

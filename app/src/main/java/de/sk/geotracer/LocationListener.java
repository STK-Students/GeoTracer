package de.sk.geotracer;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import de.sk.geotracer.data.Trip;

public class LocationListener extends LocationCallback {

    private final GoogleMap map;
    private Polyline route;
    private final Trip trip = new Trip();

    public LocationListener(GoogleMap map) {
        this.map = map;
    }

    public Trip getTrip() {
        return trip;
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
        trip.addTripLocation(position);
    }
}

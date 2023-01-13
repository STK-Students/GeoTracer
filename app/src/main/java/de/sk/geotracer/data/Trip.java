package de.sk.geotracer.data;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * An individual trip based on a journey.
 */
public class Trip {

    /**
     * This list contains the path the user traveled to get to their destination.
     * Timestamps (Instants) are mapped to locations.
     */
    private final HashMap<Instant, LatLng> tripLocations;
    private final float averageSpeed;
    private final float topSpeed;

    public Trip() {
        tripLocations = new HashMap<>();
        averageSpeed = calcAverageSpeed();
        topSpeed = calcTopSpeed();
    }

    public Trip(HashMap<Instant, LatLng> tripLocations) {
        this.tripLocations = tripLocations;
        averageSpeed = calcAverageSpeed();
        topSpeed = calcTopSpeed();
    }

    public HashMap<String, LatLng> getTripLocations() {
        HashMap<String, LatLng> convertedMap = new HashMap<>();
        tripLocations.forEach((key, value) -> convertedMap.put(key.toString(), value));
        return convertedMap;
    }

    /**
     * Adds a location with the current time stamp to the trip.
     *
     * @param location the current location of the device
     */
    public void addTripLocation(LatLng location) {
        tripLocations.put(Instant.now(), location);
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public float getTopSpeed() {
        return topSpeed;
    }

    private float calcAverageSpeed() {
        return (float) (Math.random() * 16);
    }

    private float calcTopSpeed() {
        return averageSpeed + (float) (Math.random() * 8);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (Map.Entry<Instant, LatLng> entry : tripLocations.entrySet()) {
            tmp.append(entry.getKey()).append(entry.getValue());
        }
        return tmp.toString();
    }
}

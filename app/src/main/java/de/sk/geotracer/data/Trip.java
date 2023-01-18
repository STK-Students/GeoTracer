package de.sk.geotracer.data;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * An individual trip based on a journey.
 */
public class Trip implements Comparable {

    /**
     * This list contains the path the user traveled to get to their destination.
     * Timestamps (Instants) are mapped to locations.
     */
    private final TreeMap<Instant, LatLng> tripLocations;
    private final float averageSpeed;
    private final float topSpeed;

    public Trip() {
        tripLocations = new TreeMap<>();
        averageSpeed = calcAverageSpeed();
        topSpeed = calcTopSpeed();
    }

    public Trip(TreeMap<Instant, LatLng> tripLocations) {
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

    private Float calcAverageSpeed() {
        Float speed = (float) (Math.random() * 16);
        return Float.valueOf(new DecimalFormat("0.0").format(speed));
    }

    private float calcTopSpeed() {
        float speed = 0;
        Iterator<Map.Entry<Instant, LatLng>> iterator = tripLocations.entrySet().iterator();
        Map.Entry<Instant, LatLng> currentLocation;
        Map.Entry<Instant, LatLng> nextLocation = null;
        while (iterator.hasNext()) {
            currentLocation = nextLocation;
            nextLocation = iterator.next();
            if (currentLocation != null && nextLocation != null) {
                float calcSpeed = calcSpeed(currentLocation.getValue(), currentLocation.getKey(), nextLocation.getValue(), nextLocation.getKey());
                if (calcSpeed > speed) {
                    speed = calcSpeed;
                }
            }
        }
        return speed;
    }

    private float calcSpeed(LatLng start, Instant startTimestamp, LatLng end, Instant endTimestamp) {
        float distance = calcDistance(start, end);
        Duration duration = Duration.between(startTimestamp, endTimestamp);
        return distance / (duration.toHours() + duration.toHoursPart());
    }

    private float calcDistance(LatLng start, LatLng end) {
        float[] distance = new float[2];
        Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, distance);
        float realDistance = 0;
        if (distance[0] == 0) {
            return 0;
        } else if (distance[0] >= 0) {
            realDistance = distance[0];
        } else if (distance[1] >= 0) {
            realDistance = distance[1];
        }
        return realDistance;
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

    @Override
    public int compareTo(Object o) {
        Trip otherTrip = (Trip) o;
        if (otherTrip.getAverageSpeed() < this.getAverageSpeed()) {
            return 1;
        } else if (otherTrip.getAverageSpeed() > this.getAverageSpeed()) {
            return -1;
        }
        return 0;
    }
}

package de.sk.geotracer.data;

import java.time.Instant;
import java.util.HashMap;

/**
 * An individual trip based on a journey.
 */
public class Trip {

    /**
     * This list contains the path the user traveled to get to their destination.
     * Timestamps (Instants) are mapped to locations.
     */
    HashMap<Instant, String> tripLocations;
    float averageSpeed;
    float topSpeed;

    public Trip(HashMap<Instant, String> tripLocations) {
        this.tripLocations = tripLocations;
        averageSpeed = calcAverageSpeed();
        topSpeed = calcTopSpeed();
    }

    public HashMap<Instant, String> getTripLocations() {
        return tripLocations;
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
}

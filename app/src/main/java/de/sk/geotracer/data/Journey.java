package de.sk.geotracer.data;

import java.util.List;

public class Journey {

    String name;
    List<Trip> pastTrips;

    public Journey(String name, List<Trip> pastTrips) {
        this.name = name;
        this.pastTrips = pastTrips;
    }

    public String getName() {
        return name;
    }

    public List<Trip> getPastTrips() {
        return pastTrips;
    }

    public void addTrip(Trip trip) {
        pastTrips.add(trip);
    }
}

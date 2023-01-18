package de.sk.geotracer;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import de.sk.geotracer.data.Trip;

public class Util {

    /**
     * Utility method to sort entries by value.
     * <br>
     * Based on: https://stackoverflow.com/questions/2864840/treemap-sort-by-value
     *
     * @param map to sort
     * @param <K> {@link java.time.Instant}
     * @param <V> {@link Trip}
     * @return map sorted by the average trip time
     */
    static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> sortEntriesByValue(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    Trip thisTrip = (Trip) e2.getValue();
                    Trip otherTrip = (Trip) e1.getValue();
                    if (otherTrip.getAverageSpeed() < thisTrip.getAverageSpeed()) {
                        return 1;
                    } else if (otherTrip.getAverageSpeed() > thisTrip.getAverageSpeed()) {
                        return -1;
                    }
                    return 0;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}

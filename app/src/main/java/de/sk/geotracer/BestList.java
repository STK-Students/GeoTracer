package de.sk.geotracer;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.sk.geotracer.data.Trip;


public class BestList extends AppCompatActivity {

    private static final List<Integer> IMAGES = List.of(R.drawable.platz1, R.drawable.platz2, R.drawable.platz3, R.drawable.racebike, R.drawable.letzter);
    private static final List<String> DESCRIPTION = List.of("Bester Schnitt", "Zweitbester Schnitt", "Drittbester Schnitt");
    private CustomBaseAdapter adapter;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_list);
        ListView listView = findViewById(R.id.bestListView);
        adapter = new CustomBaseAdapter(getApplicationContext(), DESCRIPTION, IMAGES);
        listView.setAdapter(adapter);

        loadData();
    }

    /**
     * Loads the data from the cloud. Then passes it on to the handler methods.
     */
    private void loadData() {
        db.collection(user.getUid()).get().addOnSuccessListener((result) -> {
            TreeMap<Instant, Trip> trips = buildTripData(result);
            runUILogic(trips);
        });
    }

    /**
     * Deserializes the data from the cloud into trips.
     *
     * @param result a database snapshot
     * @return A hashmap containing all trips
     */
    private TreeMap<Instant, Trip> buildTripData(QuerySnapshot result) {
        TreeMap<Instant, Trip> allTrips = new TreeMap<>();
        for (QueryDocumentSnapshot document : result) {
            TreeMap<Instant, LatLng> routePoints = new TreeMap<>();
            Map<String, Object> locations = document.getData();
            locations.forEach((key, value) -> {
                HashMap<String, Double> latLng = (HashMap<String, Double>) value;
                routePoints.put(Instant.parse(key), new LatLng(latLng.get("latitude"), latLng.get("longitude")));
            });
            allTrips.put(Instant.parse(document.getId()), new Trip(routePoints));
        }
        return allTrips;
    }


    private void runUILogic(Map<Instant, Trip> trips) {
        List<Trip> sortedTrips = new ArrayList<>(trips.values());
        Collections.sort(sortedTrips);
        Collections.reverse(sortedTrips);
        SortedSet<Map.Entry<Instant, Trip>> sortedMap = Util.sortEntriesByValue(trips);

        List<String> description = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy hh:mm").withZone(ZoneId.systemDefault());
        for (Map.Entry<Instant, Trip> entry : sortedMap) {
            String timestamp = formatter.format(entry.getKey());
            description.add(entry.getValue().getAverageSpeed() + "km/h\nFahrt vom " + timestamp);
        }
        adapter.setDescription(description);
        adapter.notifyDataSetChanged();
    }
}
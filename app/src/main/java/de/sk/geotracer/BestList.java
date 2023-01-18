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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import de.sk.geotracer.data.Trip;


public class BestList extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_list);

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
        trips.forEach((key, value) -> Log.i(TAG, "" + value.getTopSpeed()));
        //Run your code to show trips in the UI here.


        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        for (Map.Entry<Instant, Trip> instantTripEntry : trips.entrySet()) {
            instantTripEntry.getValue().getTopSpeed();
        }




        //ListView in der die Besten Liste angezeigt wird.
        ListView bestListView;


        //Parameter, die in der Bestenliste angezeigt werden sollen.
        String listPlatz[] = {"platz1", "platz2", "platz3"};



        //Bilder, die in der Bestenliste angezeigt werden sollen.
        int listImages[] = {R.drawable.platz1, R.drawable.platz2, R.drawable.platz3};

        //Locate the ListView
        bestListView = findViewById(R.id.bestListView);

        loadData();

        //Erstellen eines CustomBaseAdapters
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), listPlatz, listImages);
        customBaseAdapter.notifyDataSetChanged();
        //Füllen der ListView
        bestListView.setAdapter(customBaseAdapter);
        bestListView.refreshDrawableState();
    }
}
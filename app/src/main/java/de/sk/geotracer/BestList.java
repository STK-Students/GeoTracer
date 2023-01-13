package de.sk.geotracer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.sk.geotracer.data.Trip;


public class BestList extends AppCompatActivity {

    //Parameter, die in der Bestenliste angezeigt werden soll.
    String listPlatz[] = {"platz 1", "platz 2", "platz 3"};
    //Bilder, die in der Bestenliste angezeigt werden sollen.
    int listImages[] = {R.drawable.platz1, R.drawable.platz2, R.drawable.platz3};
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    //ListView
    ListView bestListView;
    /**
     * A map of all trips. The key {@link Instant} is the beginning of a route.
     */
    private HashMap<Instant, Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_best_list);
        //Lokalisieren der ListView
        bestListView = findViewById(R.id.bestListView);

        trips = getTripItems();

        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), listPlatz, listImages);
        bestListView.setAdapter(customBaseAdapter);

        /*Erstellen des Adapters für die Parameterübergabe
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_best_list_view, R.id.TextView, listPlatz);
        bestListView.setAdapter(arrayAdapter);
        */
    }

    protected HashMap<Instant, Trip> getTripItems() {
        HashMap<Instant, Trip> allTrips = new HashMap<>();
        db.collection(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    HashMap<Instant, LatLng> routePoints = new HashMap<>();
                    Map<String, Object> locations = document.getData();
                    locations.forEach((key, value) -> {
                        HashMap<String, Double> latLng = (HashMap<String, Double>) value;
                        double latitude = latLng.get("latitude");
                        double longitude = latLng.get("longitude");
                        routePoints.put(Instant.parse(key), new LatLng(latitude, longitude));
                    });
                    allTrips.put(Instant.parse(document.getId()), new Trip(routePoints));
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
        return allTrips;
    }
}
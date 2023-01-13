package de.sk.geotracer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


public class BestList extends AppCompatActivity {

    //Parameter, die in der Bestenliste angezeigt werden soll.
    String listPlatz [] = {"platz 1", "platz 2", "platz 3"};
    //Bilder, die in der Bestenliste angezeigt werden sollen.
    int listImages []= {R.drawable.platz1, R.drawable.platz2, R.drawable.platz3};
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    //ListView
    ListView bestListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTripItems();
        setContentView(R.layout.activity_best_list);
        //Lokalisieren der ListView
        bestListView = (ListView) findViewById(R.id.bestListView);

        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), listPlatz, listImages);

        bestListView.setAdapter(customBaseAdapter);

        /*Erstellen des Adapters für die Parameterübergabe
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_best_list_view, R.id.TextView, listPlatz);
        bestListView.setAdapter(arrayAdapter);
        */

    }

    protected void getTripItems(){
        db.collection(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



}
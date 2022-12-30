package de.sk.geotracer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BestList extends AppCompatActivity {

    //Parameter, die in der Bestenliste angezeigt werden soll.
    String listPlatz [] = {"platz 1", "platz 2", "platz 3"};
    //Bilder, die in der Bestenliste angezeigt werden sollen.
    int listImages []= {R.drawable.platz1, R.drawable.platz2, R.drawable.platz3};

    //ListView
    ListView bestListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
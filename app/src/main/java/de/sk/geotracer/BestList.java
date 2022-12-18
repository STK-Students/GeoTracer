package de.sk.geotracer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BestList extends AppCompatActivity {

    //Parameter, die in der Bestenliste angezeigt werden soll.
    String listPlatz [] = {"Platz 1", "Platz 2", "Platz 3", "Platz 4", "Platz 5", "Platz 6", "Platz 7", };

    //ListView
    ListView bestListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_list);

        //Lokalisieren der ListView
        bestListView = (ListView) findViewById(R.id.bestListView);

        //Erstellen des Adapters für die Parameterübergabe
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_best_list_view, R.id.TextView, listPlatz);
        bestListView.setAdapter(arrayAdapter);

    }


}
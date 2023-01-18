package de.sk.geotracer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.sk.geotracer.databinding.ActivityMainBinding;
import de.sk.geotracer.maps.MapsActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        View bestListButton = findViewById(R.id.button_bestlist);
        bestListButton.setOnClickListener(view -> {
            if(user == null){
                Toast.makeText(MainActivity.this, "Kein User angemeldet!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, BestList.class));
            }
        });

        View mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(view -> {
            if(user == null){
                Toast.makeText(MainActivity.this, "Kein User angemeldet!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, MapsActivity.class));
            }
        });

        Button loginButton = (Button)findViewById(R.id.btnOpenLogin);
        loginButton.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Boolean loginValue = extras.getBoolean("login");
            if (loginValue) {
                loginButton.setText("Logout");
            } else {
                loginButton.setText("Login");
            }
        } else {
            if(user == null){
                loginButton.setText("Login");
            } else {
                loginButton.setText("Logout");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
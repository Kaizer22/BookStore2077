package com.example.bookstore2077;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bookstore2077.data.model.LoggedInUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class StoreHome extends AppCompatActivity {

    LoggedInUser user;

    // Вкладки: магазин, моя страница, подборка, аудиокниги
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);

        Intent intent = getIntent();

        SharedPreferences sharedPreferences = getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", intent.getStringExtra("id"));
        editor.putString("login", intent.getStringExtra("login"));
        editor.putString("password", intent.getStringExtra("password"));
        editor.putString("email", intent.getStringExtra("email"));
        editor.putString("name", intent.getStringExtra("name"));
        editor.putString("surname", intent.getStringExtra("surname"));

        editor.apply();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop, R.id.navigation_personal_page, R.id.navigation_recommendations, R.id.navigation_audiobooks)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}

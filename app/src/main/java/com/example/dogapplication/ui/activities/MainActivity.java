package com.example.dogapplication.ui.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.dogapplication.R;
import com.example.dogapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupWithNavController(binding.bottomNavigation, controller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(binding.getRoot()).navigateUp();
    }
}
package com.example.projectxyz.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projectxyz.R
import com.example.projectxyz.databinding.ActivityMainBinding
import com.example.projectxyz.ui.splashscreen.SplashScreenActivity
import com.example.projectxyz.ui.splashscreen.SplashScreenViewModel
import com.example.projectxyz.utils.app.AppUtils.Companion.navigateTo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val splashScreenViewModel : SplashScreenViewModel by viewModels()
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (splashScreenViewModel.currentUser == null || splashScreenViewModel.isUserLoggedIn.value == false) {
            navigateTo(SplashScreenActivity::class.java, true)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                //R.id.navigation_patient_list,
                R.id.navigation_create_patient
            )
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_patient_list) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }

        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


}
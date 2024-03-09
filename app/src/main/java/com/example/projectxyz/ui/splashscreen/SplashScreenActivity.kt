package com.example.projectxyz.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projectxyz.databinding.ActivitySplashScreenBinding
import com.example.projectxyz.ui.MainActivity
import com.example.projectxyz.utils.app.AppUtils.Companion.navigateTo

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val TAG = "SplashScreenActivity"
    private val splashScreenViewModel : SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Sign-in button click
        binding.continueBtn.setOnClickListener {
            val email = binding.userEmailEtvl.editText?.text.toString() ?: ""
            val password = binding.userPasswordEtvl.editText?.text.toString() ?: ""
            if (email.isNotEmpty() && password.isNotEmpty()) {
                splashScreenViewModel.signIn(email, password)
                splashScreenViewModel.loginFlow.observe(this) {
                    when (it) {
                        is Resource.Loading -> {
                            Log.d(TAG, "Loading")
                            binding.progressCardView.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            Log.d(TAG, "Success")
                            binding.progressCardView.visibility = View.GONE
                            splashScreenViewModel.saveUserLoginTime(System.currentTimeMillis())
                            navigateTo(MainActivity::class.java, true)
                        }
                        is Resource.Failure -> {
                            Log.d(TAG, "Failure")
                            binding.progressCardView.visibility = View.GONE
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Log.d(TAG, "Unknown error")
                            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show()}
                    }
                }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        splashScreenViewModel.currentUser.let {
            if (it != null) {
                Log.d(TAG, "User is already signed in")
                navigateTo(MainActivity::class.java, true)
            }
        }
    }

}

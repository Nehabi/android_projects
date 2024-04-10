package com.example.practice_project

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.practice_project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("last_screen", MODE_PRIVATE)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = this.findNavController(R.id.navHostFragment)

        when(sharedPref.getInt("last_screen", -1)) {
            R.id.welcomeFragment -> navController.navigate(R.id.action_loginFragment_to_welcomeFragment)
            R.id.instructionsFragment -> navController.navigate(R.id.action_loginFragment_to_instructionsFragment)
        }
    }

    override fun onBackPressed() {
        navController.currentDestination?.let { saveState(it.id) }
        super.onBackPressed()
    }

    fun saveState(lastScreenId: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt("last_screen", lastScreenId)
        editor.apply() // or commit() - see explanation below
    }
}
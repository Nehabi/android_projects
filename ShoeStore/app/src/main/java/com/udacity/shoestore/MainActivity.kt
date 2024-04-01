package com.udacity.shoestore

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity(), LifecycleObserver {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedPref: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        //Shared preference to store the last screen
        sharedPref = getSharedPreferences(lastScreen, MODE_PRIVATE)

        //navigation
        navController = this.findNavController(R.id.navHostFragment)

        //list of to level destination to remove back button
        val topLevelDestinations: MutableSet<Int> = HashSet()
        topLevelDestinations.add(R.id.loginFragment)
        topLevelDestinations.add(R.id.welcomeFragment)
        topLevelDestinations.add(R.id.instructionsFragment)
        topLevelDestinations.add(R.id.shoeListsFragment)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations).build()

        //drawer layout - left menu
        drawerLayout = binding.drawerLayout

        //setting up NavController & ActionBar
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        //locking drawer for screens except shoe lists screen
        navController.addOnDestinationChangedListener{
                _, nd: NavDestination, _ ->
            if (nd.id == R.id.shoeListsFragment)
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            else
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        when(sharedPref.getInt(lastScreen, -1)) {
            R.id.welcomeFragment -> navController.navigate(R.id.action_loginFragment_to_welcomeFragment)
            R.id.instructionsFragment -> navController.navigate(R.id.action_loginFragment_to_instructionsFragment)
            R.id.shoeListsFragment -> navController.navigate(R.id.action_loginFragment_to_shoeListsFragment)
            R.id.shoeDetailsFragment -> navController.navigate(R.id.action_loginFragment_to_shoeDetailsFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        navController.currentDestination?.let { saveState(it.id) }
        super.onBackPressed()
    }

    private fun saveState(lastScreenId: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(lastScreen, lastScreenId)
        editor.apply()
    }

    companion object {
        private const val lastScreen = "last_screen"
    }
}

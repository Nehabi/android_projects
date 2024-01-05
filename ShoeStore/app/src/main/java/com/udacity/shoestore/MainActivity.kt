package com.udacity.shoestore

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private var appBarConfiguration: AppBarConfiguration? = null
    private var logInAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        //drawer layout - left menu
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.navHostFragment)
        //list of to level destination to remove back button
        val topLevelDestinations: MutableSet<Int> = HashSet()
        topLevelDestinations.add(R.id.loginFragment)
        topLevelDestinations.add(R.id.welcomeFragment)
        topLevelDestinations.add(R.id.instructionsFragment)

        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
            .build()

        logInAppBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()

//        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
//            if (nd.id == R.id.instructionsFragment) {
//                NavigationUI.setupActionBarWithNavController(this, navController, logInAppBarConfiguration!!)
//            } else {
//                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration!!)
//            }
//        }
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration!!)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration!!) || super.onSupportNavigateUp()
    }
}

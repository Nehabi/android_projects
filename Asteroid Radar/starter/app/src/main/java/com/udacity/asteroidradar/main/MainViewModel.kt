package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.RadarApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.Exception

class MainViewModel(val database: AsteroidDao,
                    application: Application) : AndroidViewModel(application) {
    var asteroid_list = database.getAsteroids()

    init {
        getAsteroidsLists()
    }

    private suspend fun insertInDB(asteroid: Asteroid) {
        database.insertAsteroids(asteroid)
    }

    private fun getAsteroidsLists() {
        viewModelScope.launch {
            try {
                val currentDate =  Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val startDate = dateFormat.format(currentDate).toString()
                Log.d("testing data", startDate)
                var asteroidlist = RadarApi.retrofitService.getAsteroidLists(startDate = startDate)
                val list = parseAsteroidsJsonResult(JSONObject(asteroidlist))
                for(item in list) {
                    insertInDB(item)
                }
            } catch (exception: Exception) {
                Log.e("testing", exception.toString())
            }
        }
    }
}
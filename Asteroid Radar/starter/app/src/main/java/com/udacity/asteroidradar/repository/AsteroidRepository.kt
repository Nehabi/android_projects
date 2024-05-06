package com.udacity.asteroidradar.repository

import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.RadarApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AsteroidRepository(private val database: AsteroidDatabase) {
    var asteroidList = database.asteroidDao.getAsteroids()
    suspend fun refreshAsteroids() {
        val currentDate =  Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startDate = dateFormat.format(currentDate).toString()
        val asteroidlist = RadarApi.retrofitService.getAsteroidLists(startDate = startDate)
        val list = parseAsteroidsJsonResult(JSONObject(asteroidlist))
        database.asteroidDao.insertAsteroids(list)
    }
}
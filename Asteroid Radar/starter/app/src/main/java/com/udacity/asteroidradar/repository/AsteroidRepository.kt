package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.RadarApi
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.getSeventhDate
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    val todayAsteroidList = database.asteroidDao.getTodayAsteroids(currentDate = getCurrentDate())
    val weekAsteroidList = database.asteroidDao.getWeekAsteroids(currentDate = getCurrentDate(),
                                                                 endDate = getSeventhDate())
    val savedAsteroidList = database.asteroidDao.getSavedAsteroids()
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO){
            val asteroidList = RadarApi
                .retrofitService
                .getAsteroidLists(
                    currentDate = getCurrentDate(),
                    endDate = getSeventhDate()
                )
            val list = parseAsteroidsJsonResult(JSONObject(asteroidList))
            database.asteroidDao.insertAsteroids(list)
        }
    }

    suspend fun deletePreviousData() {
        withContext(Dispatchers.IO){
            database.asteroidDao.deletePreviousAsteroid(getCurrentDate())
        }
    }
}
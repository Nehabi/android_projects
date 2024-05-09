package com.udacity.asteroidradar.repository


import com.udacity.asteroidradar.api.RadarApi
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.getSeventhDate
import com.udacity.asteroidradar.database.AsteroidDatabase
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    var todayAsteroidList = database.asteroidDao.getTodayAsteroids(currentDate = getCurrentDate())
    var weekAsteroidList = database.asteroidDao.getWeekAsteroids(currentDate = getCurrentDate(),
                                                                 endDate = getSeventhDate())
    var savedAsteroidList = database.asteroidDao.getSavedAsteroids()
    suspend fun refreshAsteroids() {
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
package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants

@Dao
interface AsteroidDao {
    @Insert
    fun testInsert(asteroidDB: Asteroid)

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME} ORDER BY id DESC LIMIT 1")
    fun testGetAsteroids(): Asteroid

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroidDB: List<Asteroid>)

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME} " +
           "WHERE close_approach_date=:currentDate ")
    fun getTodayAsteroids(currentDate: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME} WHERE id=:asteroidId")
    fun getAsteroid(asteroidId: Long): Asteroid

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME} " +
           "WHERE close_approach_date>=:currentDate " +
           "AND close_approach_date<=:endDate " +
           "ORDER BY close_approach_date ASC")
    fun getWeekAsteroids(currentDate: String, endDate: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME} " +
           "ORDER BY close_approach_date ASC")
    fun getSavedAsteroids(): LiveData<List<Asteroid>>

    @Query ("DELETE FROM ${Constants.ASTEROID_DB_TABLE_NAME} " +
            "WHERE close_approach_date<:currentDate")
    suspend fun deletePreviousAsteroid(currentDate: String)
}
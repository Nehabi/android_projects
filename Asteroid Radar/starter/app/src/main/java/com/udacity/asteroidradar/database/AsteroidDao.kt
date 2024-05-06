package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME} ORDER BY close_approach_date ASC")
    fun getAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME} WHERE id=:asteroidId")
    fun getAsteroid(asteroidId: Long): Asteroid

    @Query ("DELETE FROM ${Constants.ASTEROID_DB_TABLE_NAME}")
    suspend fun deleteAsteroids()
}
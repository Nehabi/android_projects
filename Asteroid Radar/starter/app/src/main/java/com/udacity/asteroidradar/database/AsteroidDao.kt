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
    suspend fun insertAsteroids(asteroidDB: Asteroid)

    @Query("SELECT * FROM ${Constants.ASTEROID_DB_TABLE_NAME}")
    fun getAsteroids(): LiveData<List<Asteroid>>
}
package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.ASTEROID_RADAR_DB

@Database(entities = [Asteroid::class], version=1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao : AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase?= null

        fun getInstance(context: Context) : AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                                                    AsteroidDatabase::class.java,
                                                    ASTEROID_RADAR_DB)
                                   .fallbackToDestructiveMigration()
                                   .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
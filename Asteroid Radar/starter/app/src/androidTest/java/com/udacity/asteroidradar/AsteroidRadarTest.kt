package com.udacity.asteroidradar

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AsteroidRadarTest {
    private lateinit var asteroidDao: AsteroidDao
    private lateinit var asteroidDatabase: AsteroidDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        asteroidDatabase = Room.inMemoryDatabaseBuilder(context, AsteroidDatabase::class.java)
                               .allowMainThreadQueries()
                               .build()
        asteroidDao = asteroidDatabase.asteroidDao
    }

    @After
    @Throws(Exception::class)
    fun closeDatabase() {
        asteroidDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAsteroids() {
    val asteroid = Asteroid(200, "neha", "2024-05-09", 200.0, 200.0, 200.0, 200.0, false)
            asteroidDao.testInsert(asteroid)
        assertEquals(asteroidDao.testGetAsteroids().closeApproachDate, getCurrentDate())
    }
}

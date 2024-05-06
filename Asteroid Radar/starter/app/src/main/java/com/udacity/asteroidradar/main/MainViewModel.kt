package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.RadarApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class AsteroidApiStatus { LOADING, ERROR, DONE }
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay
        get() = _imageOfDay

    private var _navigateToAsteroid = MutableLiveData<Asteroid?>()
    val navigateToAsteroid
        get() = _navigateToAsteroid

    private val _asteroidApiStatus = MutableLiveData<AsteroidApiStatus>()
    val asteroidApiStatus: LiveData<AsteroidApiStatus>
        get() = _asteroidApiStatus

    private val asteroidDatabase = AsteroidDatabase.getInstance(getApplication())

    private val asteroidRepository = AsteroidRepository(asteroidDatabase)

    init {
        _asteroidApiStatus.value = AsteroidApiStatus.LOADING
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
            getImageOfDay()
            _asteroidApiStatus.value = AsteroidApiStatus.DONE
        }
    }

    var asteroidList = asteroidRepository.asteroidList

    private fun getImageOfDay() {
        viewModelScope.launch {
            imageOfDay.value = RadarApi.retrofitService.getImageOfDay()
        }
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroid.value = asteroid
    }

    fun onAsteroidNavigated() {
        _navigateToAsteroid.value = null
    }
}
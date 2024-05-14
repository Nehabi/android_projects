package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.RadarApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class AsteroidApiStatus { LOADING, ERROR, DONE }
enum class AsteroidDateFilter { WEEK, TODAY, SAVED }
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

    private var asteroidDateFilter = MutableLiveData<AsteroidDateFilter>()

    private var asteroidDatabase = AsteroidDatabase.getInstance(getApplication())

    private val asteroidRepository = AsteroidRepository(asteroidDatabase)

    private var onlineStatus = MutableLiveData<Boolean>().apply { value = false }

    var asteroidList = asteroidDateFilter.switchMap {
        when(asteroidDateFilter.value) {
            AsteroidDateFilter.TODAY -> asteroidRepository.todayAsteroidList
            AsteroidDateFilter.WEEK -> asteroidRepository.weekAsteroidList
            AsteroidDateFilter.SAVED -> asteroidRepository.savedAsteroidList
            else -> asteroidRepository.todayAsteroidList
        }
    }

    init {
        loadData()
    }

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

    fun setAsteroidDateFilter(asteroidDateFilter: AsteroidDateFilter) {
        this.asteroidDateFilter.value = asteroidDateFilter
    }

    fun updateOnlineStatus(online: Boolean) {
        onlineStatus.value = online
        if(online && asteroidList.value.isNullOrEmpty()) {
            loadData()
        }
    }

    private fun loadData(){
        _asteroidApiStatus.value = AsteroidApiStatus.LOADING
        if(onlineStatus.value == true) {
            viewModelScope.launch {
                asteroidRepository.refreshAsteroids()
                getImageOfDay()
                setAsteroidDateFilter(AsteroidDateFilter.TODAY)
                _asteroidApiStatus.value = AsteroidApiStatus.DONE
            }
        } else {
            _asteroidApiStatus.value = AsteroidApiStatus.ERROR
        }
    }
}
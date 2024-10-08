package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RadarApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidLists(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("start_date") currentDate: String,
        @Query("start_date") endDate: String
    ): String

    @GET("planetary/apod")
    suspend fun getImageOfDay(@Query("api_key") apiKey: String = API_KEY): PictureOfDay
}

object RadarApi {
    val retrofitService : RadarApiService by lazy {
        retrofit.create(RadarApiService::class.java)
    }
}
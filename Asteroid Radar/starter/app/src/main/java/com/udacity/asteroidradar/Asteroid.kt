package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.ASTEROID_DB_TABLE_NAME)
data class Asteroid(
    @PrimaryKey
    var id: Long,
    @ColumnInfo(name = "code_name")
    var codename: String,
    @ColumnInfo(name = "close_approach_date")
    var closeApproachDate: String,
    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,
    @ColumnInfo(name = "estimated_diameter")
    var estimatedDiameter: Double,
    @ColumnInfo(name = "relative_velocity")
    var relativeVelocity: Double,
    @ColumnInfo(name = "distance_from_earth")
    var distanceFromEarth: Double,
    @ColumnInfo(name = "is_potentially_hazardous")
    var isPotentiallyHazardous: Boolean
) : Parcelable
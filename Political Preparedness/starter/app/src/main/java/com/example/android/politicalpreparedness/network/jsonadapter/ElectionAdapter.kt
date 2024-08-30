package com.example.android.politicalpreparedness.network.jsonadapter

import com.example.android.politicalpreparedness.network.models.Division
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ElectionAdapter {
    private val formats = listOf(
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
        SimpleDateFormat("yyyy-MM-dd", Locale.US)
    ).onEach { it.timeZone = TimeZone.getTimeZone("UTC") }

    @FromJson
    fun divisionFromJson (ocdDivisionId: String): Division {
        val countryDelimiter = "country:"
        val stateDelimiter = "state:"
        val country = ocdDivisionId.substringAfter(countryDelimiter,"")
                .substringBefore("/")
        val state = ocdDivisionId.substringAfter(stateDelimiter,"")
                .substringBefore("/")
        return Division(ocdDivisionId, country, state)
    }

    @ToJson
    fun divisionToJson (division: Division): String {
        return division.id
    }

    @FromJson
    fun fromJson(dateString: String?): Date? {
        return dateString?.let {
            formats.firstNotNullOfOrNull { format ->
                try {
                    format.parse(it)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    @ToJson
    fun toJson(date: Date?): String? {
        return date?.let { formats[0].format(it) }
    }
}
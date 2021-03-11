package com.example.chamaAndroid.network

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Gets Google property information from the Bar, Restaurants and Cafes API Retrofit service and updates the
 * [PlacesProperty] and [LiveData]. The Retrofit service returns a coroutine
 * Deferred, which we await to get the result of the transaction.
 * @param filter the [PlacesApiFilter] that is sent as part of the web server request
 */
@Parcelize
data class PlacesProperty(

    @Json(name = "photo_reference") val imgSrcUrl: String,
    @Json(name = "place_id") val placeId: String,
    val icon: String,
    val name: String) : Parcelable




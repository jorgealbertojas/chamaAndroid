package com.example.chamaAndroid.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotosProperty(
    @Json(name = "photo_reference") val img: String

) : Parcelable


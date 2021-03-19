package com.example.chamaAndroid.network

import android.os.Parcelable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.chamaAndroid.R
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlacesProperty(

    @Json(name = "reference") val img: String,
    @Json(name = "place_id") val placeId: String,
    val icon: String,
    val name: String,
    val price_level: Int?,
    val photos : List<PhotosProperty>?

) : Parcelable

@BindingAdapter("my_name")
fun TextView.setMyName(name: String?) {
    this.text =
        if (name.isNullOrEmpty()) "" else "${this.context.getString(R.string.key_query)} $name"
}







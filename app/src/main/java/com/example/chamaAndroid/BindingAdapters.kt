package com.example.chamaAndroid

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chamaAndroid.network.PlacesProperty
import com.example.chamaAndroid.resultPlaces.PlacesApiStatus
import com.example.chamaAndroid.resultPlaces.ResultPlacesAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PlacesProperty>?) {
    val adapter = recyclerView.adapter as ResultPlacesAdapter
    adapter.submitList(data)
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("imageFormatUrl")
fun bindFormatImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri =
            ("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$imgUrl&key=" + imgView.context.resources.getString(
                R.string.api_key
            )).toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: PlacesApiStatus?) {
    when (status) {
        PlacesApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        PlacesApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        PlacesApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

package com.example.chamaAndroid.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chamaAndroid.network.PlacesProperty

class DetailViewModelFactory(
    private val placesProperty: PlacesProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(placesProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

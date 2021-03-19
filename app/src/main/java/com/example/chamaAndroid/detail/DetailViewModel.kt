package com.example.chamaAndroid.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.chamaAndroid.R
import com.example.chamaAndroid.network.PlacesProperty

class DetailViewModel(marsProperty: PlacesProperty, app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<PlacesProperty>()

    val selectedProperty: LiveData<PlacesProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = marsProperty
    }

    val displayPropertyPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            when (it.price_level) {
                0 -> R.string.string_0free
                1 -> R.string.string_1inexpensive
                2 -> R.string.string_2moderate
                3 -> R.string.string_3expensive
                4 -> R.string.string_4very_expensive
                else -> R.string.string_not_information
            }
        )
    }

}

package com.example.chamaAndroid.resultPlaces

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chamaAndroid.R
import com.example.chamaAndroid.network.PlacesApi
import com.example.chamaAndroid.network.PlacesProperty
import kotlinx.coroutines.launch

enum class PlacesApiStatus { LOADING, ERROR, DONE }

class ResultPlacesViewModel(placeArgs: Bundle, app: Application) : ViewModel() {
    private val _status = MutableLiveData<PlacesApiStatus>()

    val status: LiveData<PlacesApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<PlacesProperty>>()

    val properties: LiveData<List<PlacesProperty>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<PlacesProperty>()

    val navigateToSelectedProperty: LiveData<PlacesProperty>
        get() = _navigateToSelectedProperty

    var key = placeArgs.getString(app.getString(R.string.key_key)).toString()
    var locations = placeArgs.getString(app.getString(R.string.key_locations)).toString()
    var query = placeArgs.getString(app.getString(R.string.key_query)).toString()

    init {
        getPlacesProperties(query, locations, key)
    }

    private fun getPlacesProperties(query: String, locationBias: String, key: String) {
        viewModelScope.launch {
            _status.value = PlacesApiStatus.LOADING
            try {
                _properties.value =
                    PlacesApi.retrofitService.getProperties(query, locationBias, key).results
                _status.value = PlacesApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PlacesApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun displayPropertyDetails(placesProperty: PlacesProperty) {
        _navigateToSelectedProperty.value = placesProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(query: String, locationBias: String, key: String) {
        getPlacesProperties(query, locationBias, key)
    }
}

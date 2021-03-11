package com.example.chamaAndroid.resultPlaces

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.chamaAndroid.network.PlacesApi
    import com.example.chamaAndroid.network.PlacesApiFilter
    import com.example.chamaAndroid.network.PlacesProperty
    import kotlinx.coroutines.launch

    enum class PlacesApiStatus { LOADING, ERROR, DONE }
    /**
     * The [ViewModel] that is attached to the [ResultPlacesFragment].
     */
    class ResultPlacesViewModel : ViewModel() {

        // The internal MutableLiveData that stores the status of the most recent request
        private val _status = MutableLiveData<PlacesApiStatus>()

        // The external immutable LiveData for the request status
        val status: LiveData<PlacesApiStatus>
            get() = _status

        // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
        // with new values
        private val _properties = MutableLiveData<List<PlacesProperty>>()

        // The external LiveData interface to the property is immutable, so only this class can modify
        val properties: LiveData<List<PlacesProperty>>
            get() = _properties

        // Internally, we use a MutableLiveData to handle navigation to the selected property
        private val _navigateToSelectedProperty = MutableLiveData<PlacesProperty>()

        // The external immutable LiveData for the navigation property
        val navigateToSelectedProperty: LiveData<PlacesProperty>
            get() = _navigateToSelectedProperty

        /**
         * Call getPlacesProperties() on init so we can display status immediately.
         */
        init {
            getPlacesProperties(PlacesApiFilter.SHOW_RESTAURANTS)
        }

        /**
         * Gets filtered Mars real estate property information from the Mars API Retrofit service and
         * updates the [PlacesProperty] [List] and [PlacesApiStatus] [LiveData]. The Retrofit service
         * returns a coroutine Deferred, which we await to get the result of the transaction.
         * @param filter the [PlacesApiFilter] that is sent as part of the web server request
         */
        private fun getPlacesProperties(filter: PlacesApiFilter) {
            viewModelScope.launch {
                _status.value = PlacesApiStatus.LOADING
                try {
                    _properties.value = PlacesApi.retrofitService.getProperties(filter.value)
                    _status.value = PlacesApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = PlacesApiStatus.ERROR
                    _properties.value = ArrayList()
                }
            }
        }

        /**
         * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
         * @param placesProperty The [PlacesProperty] that was clicked on.
         */
        fun displayPropertyDetails(placesProperty: PlacesProperty) {
            _navigateToSelectedProperty.value = placesProperty
        }

        /**
         * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
         */
        fun displayPropertyDetailsComplete() {
            _navigateToSelectedProperty.value = null
        }

        /**
         * Updates the data set filter for the web services by querying the data with the new filter
         * by calling [getPlacesProperties]
         * @param filter the [PlacesApiFilter] that is sent as part of the web server request
         */
        fun updateFilter(filter: PlacesApiFilter) {
            getPlacesProperties(filter)
        }
    }

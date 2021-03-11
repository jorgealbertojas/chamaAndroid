package com.example.chamaAndroid



import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    var placesClient: PlacesClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize the AutocompleteSupportFragment.

        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(this)


        val autocompleteFragment =
                supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                        as AutocompleteSupportFragment


        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id} , ${place.latLng?.latitude.toString()} , ${place.latLng?.longitude.toString()} ")
                 Navigation.findNavController(autocompleteFragment.requireView()).navigate(R.id.action_showResult)
            }

            override fun onError(p0: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $p0")
            }
        })

    }
}
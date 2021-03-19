
package com.example.chamaAndroid.search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.chamaAndroid.R
import com.example.chamaAndroid.databinding.FragmentSearchBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private var TAG = "MainActivity"
    private var placesClient: PlacesClient? = null

    var autocompleteFragment: AutocompleteSupportFragment? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )

        binding.edCircle

        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(this.requireActivity(), apiKey)
        }

        placesClient = Places.createClient(this.requireActivity())

        autocompleteFragment =
            this.requireActivity().supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment?.let {
            it.setPlaceFields(
                listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG
                )
            )

            it?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    // TODO: Get info about the selected place.
                    Log.i(
                        TAG,
                        "Place: ${place.name}, ${place.id} , ${place.latLng?.latitude.toString()} , ${place.latLng?.longitude.toString()} "
                    )

                    autocompleteFragment =
                        requireActivity().supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                                as AutocompleteSupportFragment

                    autocompleteFragment?.let {
                        it.requireView()!!.visibility = View.GONE
                    }

                    binding.root?.findNavController()?.navigate(
                        SearchFragmentDirections.actionSearchFragmentToResultPlacesFragment(
                            place.latLng?.latitude.toString(),
                            place.latLng?.longitude.toString(),
                            edCircle.text.toString()
                        )
                    )
                }


                override fun onError(p0: Status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: $p0")
                }
            })
        }

        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onResume() {
        autocompleteFragment?.requireView()!!.visibility = View.VISIBLE
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}

package com.example.chamaAndroid.resultPlaces

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.chamaAndroid.R
import com.example.chamaAndroid.databinding.FragmentPlacesBinding
import com.example.chamaAndroid.network.PlacesApiFilter

class ResultPlacesFragment : Fragment() {

    private val viewModel: ResultPlacesViewModel by lazy {

        val args = ResultPlacesFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(activity).application

        val bundle = bundleOf(
            getString(R.string.key_query) to PlacesApiFilter.SHOW_BARS.value + "+rating",
            getString(R.string.key_locations) to "circle:" + args.circle + "@" + args.latitude + args.longitude,
            getString(R.string.key_key) to getString(R.string.api_key)
        )


        val viewModelFactory = ResultPlacesModelFactory(bundle, application)
        ViewModelProvider(
            this, viewModelFactory
        ).get(ResultPlacesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlacesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.photosGrid.adapter = ResultPlacesAdapter(ResultPlacesAdapter.OnClickListener {
                viewModel.displayPropertyDetails(it)
            })


            viewModel.navigateToSelectedProperty.observe(this, Observer {
                if ( null != it ) {
                    this.findNavController().navigate(
                        ResultPlacesFragmentDirections.actionResultPlacesFragmentToDetailFragment(it)
                    )
                    viewModel.displayPropertyDetailsComplete()
                }
            })

            setHasOptionsMenu(true)
            return binding.root
        }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.result_places_menu, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val args = ResultPlacesFragmentArgs.fromBundle(requireArguments())

            when (item.itemId) {
                R.id.show_restaurants_menu ->
                    viewModel.updateFilter(
                        PlacesApiFilter.SHOW_RESTAURANTS.value + "+rating",
                        "circle:" + args.circle + "@" + args.latitude + args.longitude,
                        getString(R.string.api_key)
                    )
                R.id.show_cafes_menu -> viewModel.updateFilter(
                    PlacesApiFilter.SHOW_CAFES.value + "+rating",
                    "circle:" + args.circle + "@" + args.latitude + args.longitude,
                    getString(R.string.api_key)
                )
                R.id.show_bars_menu -> viewModel.updateFilter(
                    PlacesApiFilter.SHOW_BARS.value + "+rating",
                    "circle:" + args.circle + "@" + args.latitude + args.longitude,
                    getString(R.string.api_key)
                )

                else -> return false
            }

            return true
        }

}


package com.example.chamaAndroid.resultPlaces

    import android.os.Bundle
    import android.view.*
    import androidx.fragment.app.Fragment
    import androidx.lifecycle.Observer
    import androidx.lifecycle.ViewModelProvider
    import androidx.navigation.fragment.findNavController
    import com.example.chamaAndroid.R
    import com.example.chamaAndroid.databinding.FragmentPlacesBinding
    import com.example.chamaAndroid.network.PlacesApiFilter

/**
     * This fragment shows the the status of the Mars real-estate web services transaction.
     */
    class ResultPlacesFragment : Fragment() {

        /**
         * Lazily initialize our [ResultPlacesViewModel].
         */
        private val viewModel: ResultPlacesViewModel by lazy {
            ViewModelProvider(this).get(ResultPlacesViewModel::class.java)
        }

        /**
         * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
         * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val binding = FragmentPlacesBinding.inflate(inflater)

            // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
            binding.lifecycleOwner = this

            // Giving the binding access to the OverviewViewModel
            binding.viewModel = viewModel

            // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
            // tells the viewModel when our property is clicked
            binding.photosGrid.adapter = ResultPlacesAdapter(ResultPlacesAdapter.OnClickListener {
                viewModel.displayPropertyDetails(it)
            })

            // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
            // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
            // for another navigation event.
            viewModel.navigateToSelectedProperty.observe(this, Observer {
                if ( null != it ) {
                    // Must find the NavController from the Fragment
                   // this.findNavController().navigate(ResultPlacesFragmentDirections.actionShowDetail(it))
                    // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                    viewModel.displayPropertyDetailsComplete()
                }
            })

            setHasOptionsMenu(true)
            return binding.root
        }

        /**
         * Inflates the result places menu that contains filtering options.
         */
        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.result_places_menu, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }

        /**
         * Updates the filter in the [ResultPlacesViewModel] when the menu items are selected from the
         * overflow menu.
         */
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            viewModel.updateFilter(
                when (item.itemId) {
                    R.id.show_restaurants_menu -> PlacesApiFilter.SHOW_RESTAURANTS
                    R.id.show_cafes_menu -> PlacesApiFilter.SHOW_CAFES
                    else -> PlacesApiFilter.SHOW_BARS
                }
            )
            return true
        }
    }


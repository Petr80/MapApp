package com.petr.example.mapapp.ui.map

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.petr.example.mapapp.MainActivity
import com.petr.example.mapapp.R
import com.petr.example.mapapp.databinding.FragmentMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*


@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private var permissionDenied = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val mapViewModel: MapViewModel by viewModels()

    //#1 Defining a BottomSheetBehavior instance
    private lateinit var bottomSheetBehaviorList: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetBehaviorItem: BottomSheetBehavior<CoordinatorLayout>

    private var lat: Float = 0f
    private var lon: Float = 0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        createBottomSheetListBehaviour()
        createBottomSheetItemBehavior()
        bottomSheetBehaviorList.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehaviorItem.state = BottomSheetBehavior.STATE_HIDDEN

        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        setupRecyclerView()
        subscribeUi()
    }

    private fun subscribeUi() {
        mapViewModel.items.observe(viewLifecycleOwner) { result ->
            binding.includedItemList.hasItems = !result.isNullOrEmpty()
            (binding.includedItemList.bottomMapRecycleList.adapter as MapListAdapter).submitList(result)

            // for cycle add markers hhghghgh
        }
    }
    
    private fun setupRecyclerView() {
        val listAdapter = MapListAdapter()
        binding.includedItemList.bottomMapRecycleList.adapter = listAdapter

        val llm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.includedItemList.bottomMapRecycleList.layoutManager = llm

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.includedItemList.bottomMapRecycleList)
        binding.includedItemList.bottomMapRecycleList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val first = llm.findFirstVisibleItemPosition()
                val second = llm.findLastVisibleItemPosition()

                if (first != second)
                    return super.onScrolled(recyclerView, dx, dy)


                // hardcoded for now... todo: make dynamic
                val vrane = LatLng(50.24107, 14.31149)
                val zoomLevel = 15f
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vrane, zoomLevel))

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setupUI() {
        binding.fabList.setOnClickListener {
            bottomSheetBehaviorList.state =
                if (bottomSheetBehaviorList.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_COLLAPSED
                else
                    BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap ?: return

        val vrane = LatLng(49.93996797646046, 14.38521025346187)
        val zoomLevel = 15f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vrane, zoomLevel))
        mMap.addMarker(
            MarkerOptions()
                .position(vrane)
                .title("Home sweet Home")
        )
        setMapLongClick(mMap)
        setOnCameraMove(vrane)
    }

    private fun setOnCameraMove(vrane: LatLng){
        mMap.setOnCameraMoveListener {
            // iterate over all items

            if (mMap.projection.visibleRegion.latLngBounds.contains(vrane)){
                // show find others
            }
        }
    }

    private fun setMapLongClick(mMap: GoogleMap) {
        mMap.cameraPosition
        mMap.setOnMapLongClickListener {
            val snippet = String.format(
                Locale.getDefault(),
                "LAT: %1$.5f, LONG: %2$.5f",
                it.latitude,
                it.longitude
            )
            mMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("LOCATION")
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )

            bottomSheetBehaviorItem.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            hideFAB()

            binding.includedItem.tvLattitude.text = it.latitude.toString()
            binding.includedItem.tvLongitude.text = it.longitude.toString()

            binding.includedItem.fabSave.setOnClickListener {
                Toast.makeText(requireContext(), "FAB SAVE CLICK", Toast.LENGTH_SHORT).show()
            }

            binding.includedItem.imageButtonClose.setOnClickListener {
                bottomSheetBehaviorItem.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun hideBottomNavView() {
        val parentActivity: MainActivity = activity as MainActivity
        val view: BottomNavigationView = parentActivity.nav_view

        if (view.visibility == View.VISIBLE)
            view.visibility = View.GONE
    }

    private fun showBottomNavView() {
        val parentActivity: MainActivity = activity as MainActivity
        val view: BottomNavigationView = parentActivity.nav_view

        if (view.visibility == View.GONE)
            view.visibility = View.VISIBLE
    }

    private fun hideFAB() {
        val parentActivity: MainActivity = activity as MainActivity
        val view: View = parentActivity.fab_list

        if (view.visibility == View.VISIBLE)
            view.visibility = View.GONE
    }

    private fun showFAB() {
        val parentActivity: MainActivity = activity as MainActivity
        val view: View = parentActivity.fab_list

        if (view.visibility == View.GONE)
            view.visibility = View.VISIBLE
    }

    private fun createBottomSheetListBehaviour() {
        bottomSheetBehaviorList = BottomSheetBehavior.from(binding.includedItemList.bottomMapList)
        // bottomSheetBehaviorList.isFitToContents = false
        // bottomSheetBehaviorList.halfExpandedRatio = 0.4f
        bottomSheetBehaviorList.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.fabList.setImageResource(R.drawable.outline_close_black_24)
                        // findNavController().navigate(R.id.action_navigation_maps_to_navigation_dashboard)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.fabList.setImageResource(R.drawable.fab_list_black_24)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
/*                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.fabList.setImageResource(R.drawable.fab_list_black_24)
                        Toast.makeText(requireContext(), "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
                    }*/
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
        })
    }

    private fun createBottomSheetItemBehavior() {
        bottomSheetBehaviorItem = BottomSheetBehavior.from(binding.includedItem.bottomSheetItem)
        bottomSheetBehaviorItem.isFitToContents = false
        bottomSheetBehaviorItem.halfExpandedRatio = 0.4f
        bottomSheetBehaviorItem.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
/*                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Toast.makeText(requireContext(), "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
                        showBottomNavView()
                        showFAB()
                    }*/
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        hideBottomNavView()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        showBottomNavView()
                        showFAB()
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
        })
    }

}
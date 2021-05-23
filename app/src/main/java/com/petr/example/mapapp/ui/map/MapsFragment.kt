package com.petr.example.mapapp.ui.map

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
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
import com.petr.example.mapapp.ui.common.slideDown
import com.petr.example.mapapp.ui.common.slideUp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var permissionDenied = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding

    //#1 Defining a BottomSheetBehavior instance
    private lateinit var bottomSheetBehaviorList: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetBehaviorItem: BottomSheetBehavior<CoordinatorLayout>

    private var lat: Float = 0f
    private var lon: Float = 0f

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

    }

    private fun setMapLongClick(mMap: GoogleMap) {
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
                    .title("TEXT")
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )

            bottomSheetBehaviorItem.state = BottomSheetBehavior.STATE_EXPANDED
            hideFAB()

            binding.includedItem.fabSave.setOnClickListener{
                val a = 0
            }

        }

    }

    private fun hideBottomNavView(){
        val parentActivity: MainActivity = activity as MainActivity
        val view: BottomNavigationView = parentActivity.nav_view

        if (view.visibility==View.VISIBLE)
            view.visibility = View.GONE
    }

    private fun hideFAB(){
        val parentActivity: MainActivity = activity as MainActivity
        val view: View = parentActivity.fab_list

        if (view.visibility==View.VISIBLE)
            view.visibility = View.GONE
    }

    private fun createBottomSheetBehaviorItemInstance(){
        bottomSheetBehaviorItem = BottomSheetBehavior.from(binding.includedItem.itemEdit)
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
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        hideBottomNavView()
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
        })
    }

    private fun createBottomSheetListBehaviour(){

        bottomSheetBehaviorList = BottomSheetBehavior.from(binding.includedItemList.bottomSheetList)
        bottomSheetBehaviorList.isFitToContents = false
        bottomSheetBehaviorList.halfExpandedRatio = 0.4f
        bottomSheetBehaviorList.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        findNavController().navigate(R.id.action_navigation_maps_to_navigation_dashboard)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        hideBottomNavView()
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
        })

    }

    private fun setupUI(){
        binding.fabList.setOnClickListener {
            val state =
                if (bottomSheetBehaviorList.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_HIDDEN
                else
                    BottomSheetBehavior.STATE_HALF_EXPANDED
            bottomSheetBehaviorList.state = state
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        createBottomSheetBehaviorItemInstance()
        createBottomSheetListBehaviour()
        bottomSheetBehaviorItem.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehaviorList.state = BottomSheetBehavior.STATE_HIDDEN

        setupUI()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
}
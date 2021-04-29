package com.petr.example.mapapp.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.petr.example.mapapp.R
import com.petr.example.mapapp.databinding.FragmentMapsBinding
import kotlinx.android.synthetic.main.fragment_items_list.*
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var permissionDenied = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    //#1 Defining a BottomSheetBehavior instance
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap ?: return

        val vrane = LatLng(49.93996797646046, 14.38521025346187)
        val zoomLevel = 15f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vrane, zoomLevel))
        mMap.addMarker(MarkerOptions()
            .position(vrane)
            .title("Home sweet Home"))

        setMapLongClick(mMap)
    }

    private fun setMapLongClick(mMap: GoogleMap) {
        mMap.setOnMapClickListener {
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
/*            hideBottomNavView()
            createEditBottomSheet() // from EditFragment */
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        //#2 Initializing the BottomSheetBehavior
/*        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetList)
        //#3 Listening to State Changes of BottomSheet
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {
*//*                binding.fabList.setImageIcon(icon: Icon) = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> "Close Persistent Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED -> "Open Persistent Bottom Sheet"
                    else -> "Persistent Bottom Sheet"
                }*//*
            }
        })

        //#4 Changing the BottomSheet State on ButtonClick
        binding.fabList.setOnClickListener {
            val state =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_COLLAPSED
                else
                    BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.state = state
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
}
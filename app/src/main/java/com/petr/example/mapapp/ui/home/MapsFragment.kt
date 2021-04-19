package com.petr.example.mapapp.ui.home

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.petr.example.mapapp.R
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var permissionDenied = false
    private lateinit var mMap: GoogleMap

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
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
}
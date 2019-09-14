package com.example.tdmproject


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_nearest.*
import kotlinx.android.synthetic.main.fragment_nearest.view.*


class NearestFragment : Fragment() , OnMapReadyCallback {
    override fun onMapReady(p0: GoogleMap?) {
        p0?.addMarker(MarkerOptions().position(LatLng(35.1,3.15)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_nearest, container, false)
        val supprtMap = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supprtMap.getMapAsync(this)
        return view
    }


}

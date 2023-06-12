package com.example.mapplayground

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

/**
 * Demonstration that if a user has Google Location Accuracy turned off and a MapView is shown
 * **before** the location permission is requested, then the user's location will not show on the map.
 *
 * If the MapView is shown **after** the location permission is requested, then the user's location will
 * show on the map.
 */
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    // Flip this to toggle between the scenarios.
    private val showMapBeforeLocationPermissionRequest = true

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val mapFragment: SupportMapFragment
                if (showMapBeforeLocationPermissionRequest) {
                    mapFragment =
                        supportFragmentManager.findFragmentById(android.R.id.content) as SupportMapFragment
                } else {
                    mapFragment = SupportMapFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(android.R.id.content, mapFragment)
                        .commit()
                }
                mapFragment.getMapAsync(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (showMapBeforeLocationPermissionRequest) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SupportMapFragment())
                .commit()
        }
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        map.isMyLocationEnabled = true
    }
}
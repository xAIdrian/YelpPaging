package com.adrian.weedmapschallenge.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject


class LocationHelper @Inject constructor(
    private val context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var lastFetchedLocation: Location? = null

    private fun isLocationEnabled(): Boolean = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    /**
     * We are checking for location availability and then launching our own asynchronous operation
     * to get the actual location once we
     */
    @SuppressLint("MissingPermission")
    fun getUsersLastLocation(getFreshLocation: Boolean = false): Location? {

        if (lastFetchedLocation != null && !getFreshLocation) {
            return lastFetchedLocation
        } else if (isLocationEnabled()) {
            fusedLocationClient.locationAvailability
                .addOnSuccessListener { locationAvailability ->
                    if (locationAvailability.isLocationAvailable) {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            lastFetchedLocation = location
                        }
                    }
                }
        }
        return lastFetchedLocation
    }
}
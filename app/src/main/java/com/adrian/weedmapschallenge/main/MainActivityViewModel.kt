package com.adrian.weedmapschallenge.main

import androidx.lifecycle.ViewModel
import com.adrian.weedmapschallenge.common.LocationHelper
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val locationHelper: LocationHelper
) : ViewModel() {

    fun getUserLocation() {
        locationHelper.getUsersLastLocation(true)
    }
}

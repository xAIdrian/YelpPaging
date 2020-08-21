package com.adrian.weedmapschallenge.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.domain.FusionRepository
import io.reactivex.Flowable
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val repository: FusionRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    val successfulLocationUpdateLiveData = MutableLiveData<Boolean>()
    val errorToastLiveData = MutableLiveData<String>()
    val emptyResultsLiveData = MutableLiveData<Boolean>()

    fun searchYelp(searchTerm: CharSequence): Flowable<PagingData<Business>> {
        val location = locationHelper.getUsersLastLocation()

        return repository.getBusinessSearchResponse(
            searchTerm.toString(),
            location?.latitude ?: NEW_YORK_LATITUDE,
            location?.longitude ?: NEW_YORK_LONGITUDE
        ).cachedIn(viewModelScope)
    }

    fun updateLocation() {
        successfulLocationUpdateLiveData.value =
            locationHelper.getUsersLastLocation(true) != null
    }

    companion object {
        private const val NEW_YORK_LATITUDE = 40.712776
        private const val NEW_YORK_LONGITUDE = -74.005974
    }
}
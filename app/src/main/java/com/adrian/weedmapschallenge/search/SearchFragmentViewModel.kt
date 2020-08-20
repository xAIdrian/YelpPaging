package com.adrian.weedmapschallenge.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.domain.FusionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val repository: FusionRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    val businessesLiveData = MutableLiveData<List<Business>>()
    val successfulLocationUpdateLiveData = MutableLiveData<Boolean>()
    val errorToastLiveData = MutableLiveData<String>()
    val emptyResultsLiveData = MutableLiveData<Boolean>()

    @SuppressLint("CheckResult")
    fun getSearchResults(searchTerm: CharSequence) {
        val location = locationHelper.getUsersLastLocation()

        repository.getBusinessSearchResponse(
            searchTerm.toString(),
            location?.latitude ?: NEW_YORK_LATITUDE,
            location?.longitude ?: NEW_YORK_LONGITUDE,
            location.toString()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                errorToastLiveData.value = it.message
            }
            .doOnSuccess { result ->
                Log.e("test", result.size.toString())
            }
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
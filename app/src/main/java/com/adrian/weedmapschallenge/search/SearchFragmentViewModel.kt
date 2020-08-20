package com.adrian.weedmapschallenge.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.data.SearchResponse
import com.adrian.weedmapschallenge.domain.FusionRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val repository: FusionRepository,
    private val locationHelper: LocationHelper
): ViewModel() {

    val businessesLiveData = MutableLiveData<List<Business>>()
    val successfulLocationUpdateLiveData = MutableLiveData<Boolean>()
    val errorToastLiveData = MutableLiveData<String>()
    val emptyResultsLiveData = MutableLiveData<Boolean>()

    fun getSearchResults(searchTerm: CharSequence) {
        viewModelScope.launch {
            val location = locationHelper.getUsersLastLocation()

            repository.getBusinessSearchResponse(
                searchTerm.toString(),
                location?.latitude ?: NEW_YORK_LATITUDE,
                location?.longitude ?: NEW_YORK_LONGITUDE,
                location.toString()
            ).enqueue(object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    throw(t)
                }
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful && response.body()?.total == 0)
                        emptyResultsLiveData.value = true
                    if (response.isSuccessful) {
                        businessesLiveData.value = response.body()?.businesses
                    } else {
                        errorToastLiveData.value = response.errorBody().toString()
                    }
                }
            })
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
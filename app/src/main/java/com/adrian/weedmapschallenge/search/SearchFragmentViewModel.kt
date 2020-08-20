package com.adrian.weedmapschallenge.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.weedmapschallenge.common.LocationHelper
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

    fun getSearchResults(searchTerm: String) {
        viewModelScope.launch {
            repository.getBusinessSearchResponse(
                "Starbucks",
                40.712776,
                -74.005974,
                null
            ).enqueue(object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    throw(t)
                }
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    Log.e("class here", response.toString())
                }
            })
        }
    }

}
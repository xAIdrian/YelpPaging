package com.adrian.weedmapschallenge.domain

import com.adrian.weedmapschallenge.data.SearchResponse
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val yelpFusionClient: YelpFusionClient
) : IMainRepository {

    override suspend fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double,
        location: String?
    ): Call<SearchResponse> {
        return yelpFusionClient.callForSearchResults(latitude, longitude, location)
    }

}
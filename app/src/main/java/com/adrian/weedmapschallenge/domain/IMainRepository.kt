package com.adrian.weedmapschallenge.domain

import com.adrian.weedmapschallenge.data.SearchResponse
import retrofit2.Call

interface IMainRepository {

    suspend fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double,
        location: String?
    ): Call<SearchResponse>
}
package com.adrian.weedmapschallenge.domain

import com.adrian.weedmapschallenge.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface YelpFusionClient {
    @Headers("Authorization: Bearer nSjZvYu7ncMILEIghK72grZf7EAIeph8UsUMbZT0aGCCFMdhmc-cZIs3_NuHv-eIPRskeYZoVp7rgU6jLvenXv-rDeXoni5rKfNARFFOGyH7zio0PQk49hKcjII7X3Yx")
    @GET("/v3/businesses/search")
    fun callForSearchResults(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("location") location: String?
    ): Call<SearchResponse>
}
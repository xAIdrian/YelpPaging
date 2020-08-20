package com.adrian.weedmapschallenge.domain

import com.adrian.weedmapschallenge.data.ReviewResponse
import com.adrian.weedmapschallenge.data.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpFusionClient {
    @Headers("Authorization: Bearer nSjZvYu7ncMILEIghK72grZf7EAIeph8UsUMbZT0aGCCFMdhmc-cZIs3_NuHv-eIPRskeYZoVp7rgU6jLvenXv-rDeXoni5rKfNARFFOGyH7zio0PQk49hKcjII7X3Yx")
    @GET("/v3/businesses/search")
    fun getSearchResults(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("location") location: String?
    ): Observable<SearchResponse>

    @Headers("Authorization: Bearer nSjZvYu7ncMILEIghK72grZf7EAIeph8UsUMbZT0aGCCFMdhmc-cZIs3_NuHv-eIPRskeYZoVp7rgU6jLvenXv-rDeXoni5rKfNARFFOGyH7zio0PQk49hKcjII7X3Yx")
    @GET("/v3/businesses/{id}/reviews")
    fun getBusinessReviews(
        @Path("id") id: String
    ): Observable<ReviewResponse>
}
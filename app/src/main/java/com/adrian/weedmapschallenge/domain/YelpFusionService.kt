package com.adrian.weedmapschallenge.domain

import com.adrian.weedmapschallenge.data.ReviewsResponse
import com.adrian.weedmapschallenge.data.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpFusionService {
    @Headers("Authorization: Bearer nSjZvYu7ncMILEIghK72grZf7EAIeph8UsUMbZT0aGCCFMdhmc-cZIs3_NuHv-eIPRskeYZoVp7rgU6jLvenXv-rDeXoni5rKfNARFFOGyH7zio0PQk49hKcjII7X3Yx")
    @GET("/v3/businesses/search")
    fun getSearchResults(
        @Query("term") term: String?,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("location") location: String?,
        @Query("offset") offset: Int? = 0
    ): Observable<SearchResponse>

    @Headers("Authorization: Bearer nSjZvYu7ncMILEIghK72grZf7EAIeph8UsUMbZT0aGCCFMdhmc-cZIs3_NuHv-eIPRskeYZoVp7rgU6jLvenXv-rDeXoni5rKfNARFFOGyH7zio0PQk49hKcjII7X3Yx")
    @GET("/v3/businesses/{id}/reviews")
    fun getBusinessReviews(
        @Path("id") id: String
    ): Observable<ReviewsResponse>
}
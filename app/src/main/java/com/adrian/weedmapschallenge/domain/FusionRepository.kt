package com.adrian.weedmapschallenge.domain

import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.data.Reviews
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FusionRepository @Inject constructor(
    private val yelpFusionClient: YelpFusionClient
) : IFusionRepository {

    override fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double,
        location: String?
    ): Single<List<Pair<Business, Reviews>>>{
        return yelpFusionClient.getSearchResults(latitude, longitude, location)
            .flatMap { searchResponse ->
                Observable.fromIterable(searchResponse.businesses).flatMap { item ->
                    yelpFusionClient.getBusinessReviews(item.id!!).map { bus -> item to bus.reviews.first() }
                }
            }.toList()
    }
}
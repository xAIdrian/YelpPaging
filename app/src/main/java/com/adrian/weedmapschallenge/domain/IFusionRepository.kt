package com.adrian.weedmapschallenge.domain

import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.data.Reviews
import io.reactivex.Single

interface IFusionRepository {

    fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double,
        location: String?
    ): Single<List<Pair<Business, Reviews>>>
}
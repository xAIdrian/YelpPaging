package com.adrian.weedmapschallenge.domain

import androidx.paging.PagingData
import com.adrian.weedmapschallenge.data.Business
import io.reactivex.Flowable

interface IFusionRepository {

    fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double
    ): Flowable<PagingData<Business>>
}
package com.adrian.weedmapschallenge.domain

import androidx.paging.PagingData
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.data.Reviews
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface IFusionRepository {

    fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double,
        location: String?
    ): Flowable<PagingData<Business>>
}
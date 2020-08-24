package com.adrian.weedmapschallenge.domain

import androidx.paging.PagingData
import com.adrian.weedmapschallenge.data.Business
import io.reactivex.Flowable
import io.reactivex.Observable

interface IFusionRepository {

    fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double
    ): Observable<PagingData<Business>>
}
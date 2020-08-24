package com.adrian.weedmapschallenge.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import androidx.paging.rxjava2.observable
import com.adrian.weedmapschallenge.data.Business
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FusionRepository @Inject constructor(
    private val yelpFusionService: YelpFusionService
) : IFusionRepository {

    override fun getBusinessSearchResponse(
        term: String,
        latitude: Double,
        longitude: Double
    ): Observable<PagingData<Business>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FusionPagingSource(yelpFusionService, term, latitude, longitude)
            }
        ).observable
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
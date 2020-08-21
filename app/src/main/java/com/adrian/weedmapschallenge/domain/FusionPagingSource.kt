package com.adrian.weedmapschallenge.domain

import android.util.Log
import androidx.paging.rxjava2.RxPagingSource
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.data.Reviews
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FusionPagingSource @Inject constructor(
    private val yelpFusionService: YelpFusionService,
    private val query: String,
    private val latitude: Double,
    private val longitude: Double
) : RxPagingSource<Int, Business>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Business>> {
        val pagingOffsetKey = params.key ?: STARTING_OFFSET_VALUE
        return yelpFusionService.getSearchResults(query, latitude, longitude, pagingOffsetKey)
            .subscribeOn(Schedulers.io())
            .flatMap { searchResponse ->

                Observable.fromIterable(searchResponse.businesses)
                    .throttleFirst(200, TimeUnit.MILLISECONDS)
                    .flatMap { item ->
                        yelpFusionService.getBusinessReviews(item.id!!)
                            .map { bus -> item to bus.reviews.firstOrNull() }
                    }
            }
            .toList()
            .map { pairedList -> pairBusinessWithReviews(pairedList) }
            .map { businesses -> toLoadResult(businesses, pagingOffsetKey) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(businesses: ArrayList<Business>, pagingOffsetKey: Int): LoadResult<Int, Business> {
        return LoadResult.Page(
            data = businesses,
            prevKey = if (pagingOffsetKey == STARTING_OFFSET_VALUE) null else pagingOffsetKey - OFFSET_VALUE,
            nextKey = if (businesses.isEmpty()) null else pagingOffsetKey + OFFSET_VALUE
        )
    }

    private fun pairBusinessWithReviews(pairedList: List<Pair<Business, Reviews?>>): ArrayList<Business> {
        val reviewedBusinessList = ArrayList<Business>()
        pairedList.forEach {
            if (it.second != null) it.first.bestReview = it.second
            reviewedBusinessList.add(it.first)
        }
        return reviewedBusinessList
    }

    companion object {
        private const val STARTING_OFFSET_VALUE = 0
        private const val OFFSET_VALUE = 20
    }
}
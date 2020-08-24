package com.adrian.weedmapschallenge.search

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.domain.FusionRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val repository: FusionRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    val successfulLocationUpdateLiveData = MutableLiveData<Boolean>()
    val errorToastLiveData = MutableLiveData<String>()
    val emptyResultsLiveData = MutableLiveData<Boolean>()

    fun searchYelp(searchTerm: CharSequence): Observable<PagingData<Business>> {
        return locationHelper.getUsersLastLocation()
            .flatMapObservable { location ->
                repository.getBusinessSearchResponse(
                    searchTerm.toString(),
                    location.latitude,
                    location.longitude
                ).cachedIn(viewModelScope)
            }.doOnError { throwable -> errorToastLiveData.value = throwable.message }
    }

    @SuppressLint("CheckResult")
    fun updateLocation() {
        locationHelper
            .getUsersLastLocation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> errorToastLiveData.value = throwable.message }
            .subscribe { location ->
                successfulLocationUpdateLiveData.value = location != null
            }
    }

    companion object {
        private const val NEW_YORK_LATITUDE = 40.712776
        private const val NEW_YORK_LONGITUDE = -74.005974
    }
}
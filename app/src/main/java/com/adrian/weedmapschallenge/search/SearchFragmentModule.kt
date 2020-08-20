package com.adrian.weedmapschallenge.search

import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.domain.FusionRepository
import dagger.Module
import dagger.Provides

@Module
class SearchFragmentModule {

    @Provides
    fun provideSearchFragmentViewModel(
        repository: FusionRepository,
        locationHelper: LocationHelper
    ): SearchFragmentViewModel {
        return SearchFragmentViewModel(
            repository,
            locationHelper
        )
    }
}
package com.adrian.weedmapschallenge.main

import com.adrian.weedmapschallenge.common.LocationHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule {

    @Provides
    @Singleton
    fun provideMainActivityViewModel(locationHelper: LocationHelper): MainActivityViewModel {
        return MainActivityViewModel(locationHelper)
    }
}
package com.adrian.weedmapschallenge.dagger

import android.app.Application
import android.content.Context
import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.domain.RetrofitServiceFactory
import com.adrian.weedmapschallenge.domain.YelpFusionClient
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    @Named("application.context")
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideYelpFusionClient() = RetrofitServiceFactory.createService(YelpFusionClient::class.java)

    @Provides
    @Singleton
    fun provideLocationManager(context: Context) = LocationHelper(context)
}
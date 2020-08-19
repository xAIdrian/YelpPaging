package com.adrian.weedmapschallenge.main

import com.adrian.weedmapschallenge.domain.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule {

    @Provides
    @Singleton
    fun provideMainViewModel(repository: MainRepository): MainViewModel {
        return MainViewModel(repository)
    }
}
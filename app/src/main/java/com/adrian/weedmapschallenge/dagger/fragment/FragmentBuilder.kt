package com.adrian.weedmapschallenge.dagger.fragment

import com.adrian.weedmapschallenge.main.DetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector()
    abstract fun provideDetailsFragment(): DetailsFragment
}
package com.adrian.weedmapschallenge.dagger.fragment

import com.adrian.weedmapschallenge.search.SearchFragment
import com.adrian.weedmapschallenge.search.SearchFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [
        SearchFragmentModule::class
    ])
    abstract fun provideSearchFragment(): SearchFragment
}
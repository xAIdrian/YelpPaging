package com.adrian.weedmapschallenge.dagger.activity

import com.adrian.weedmapschallenge.main.MainActivity
import com.adrian.weedmapschallenge.dagger.fragment.FragmentBuilder
import com.adrian.weedmapschallenge.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [
        MainActivityModule::class,
        FragmentBuilder::class
    ])
    abstract fun bindMainActivity(): MainActivity
}
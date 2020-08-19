package com.adrian.weedmapschallenge.dagger

import android.content.Context
import com.adrian.weedmapschallenge.WeedmapsApp
import com.adrian.weedmapschallenge.dagger.activity.ActivityBuilder
import com.adrian.weedmapschallenge.dagger.fragment.FragmentBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityBuilder::class,
    FragmentBuilder::class
])
interface ApplicationComponent : AndroidInjector<WeedmapsApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
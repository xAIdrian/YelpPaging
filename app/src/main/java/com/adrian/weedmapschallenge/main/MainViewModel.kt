package com.adrian.weedmapschallenge.main

import androidx.lifecycle.ViewModel
import com.adrian.weedmapschallenge.domain.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    repository: MainRepository
): ViewModel() {

}
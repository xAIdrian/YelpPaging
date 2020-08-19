package com.adrian.weedmapschallenge.data

data class SearchResponse (
    val total: Int = 0,
    val businesses: List<Business>?,
    val region: Region?
)
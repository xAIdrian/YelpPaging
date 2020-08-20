package com.adrian.weedmapschallenge.data

data class ReviewResponse (
    var total: String?,
    var possible_languages: Array<String>,
    var reviews: Array<Reviews>
)
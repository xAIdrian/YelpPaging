package com.adrian.weedmapschallenge.data

data class ReviewsResponse (
    var total: String?,
    var possible_languages: Array<String>,
    var reviews: Array<Reviews>
)
package com.adrian.weedmapschallenge.data

data class Business(
    val rating: Double = 0.0,
    val price: String?,
    val phone: String?,
    val id: String?,
    val alias: String?,
    val is_closed: Boolean = false,
    val categories: List<Category>?,
    val review_count: Int = 0,
    val name: String?,
    val url: String?,
    val coordinates: Coordinates?,
    val image_url: String?,
    val location: Location?,
    val distance: Double = 0.0,
    val transactions: List<String>?
)
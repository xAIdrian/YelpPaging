package com.adrian.weedmapschallenge.data

import com.google.gson.annotations.SerializedName

data class Business(
    val rating: Double = 0.0,
    val price: String?,
    val phone: String?,
    val id: String?,
    val alias: String?,
    @SerializedName("is_closed")
    val isClosed: Boolean = false,
    val categories: List<Category>?,
    @SerializedName("review_count")
    val reviewCount: Int = 0,
    val name: String?,
    val url: String?,
    val coordinates: Coordinates?,
    @SerializedName("image_url")
    val imageUrl: String?,
    val location: Location?,
    val distance: Double = 0.0,
    val transactions: List<String>?,
    var bestReview: Reviews?
)
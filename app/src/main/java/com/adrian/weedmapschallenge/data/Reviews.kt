package com.adrian.weedmapschallenge.data

import com.google.gson.annotations.SerializedName

data class Reviews (
    var rating: String?,
    @SerializedName("time_created")
    var timeCreated: String?,
    var id: String?,
    var text: String?,
    var user: User?,
    var url: String?
)
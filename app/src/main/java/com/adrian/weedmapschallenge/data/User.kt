package com.adrian.weedmapschallenge.data

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("profile_url")
    var profileUrl: String?,
    @SerializedName("image)url")
    var imageUrl: String?,
    var name: String?,
    var id: String?
)
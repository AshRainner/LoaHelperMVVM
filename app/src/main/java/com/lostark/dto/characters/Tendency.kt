package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class Tendency(
    @SerializedName("MaxPoint")
    val maxPoint: Int,
    @SerializedName("Point")
    val point: Int,
    @SerializedName("Type")
    val type: String
)
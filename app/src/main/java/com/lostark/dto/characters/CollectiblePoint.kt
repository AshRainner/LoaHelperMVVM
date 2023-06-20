package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class CollectiblePoint(
    @SerializedName("MaxPoint")
    val maxPoint: Int,
    @SerializedName("Point")
    val point: Int,
    @SerializedName("PointName")
    val pointName: String
)
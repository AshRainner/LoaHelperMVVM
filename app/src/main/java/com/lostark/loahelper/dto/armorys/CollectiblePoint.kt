package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CollectiblePoint(
    @SerializedName("MaxPoint")
    val maxPoint: Int,
    @SerializedName("Point")
    val point: Int,
    @SerializedName("PointName")
    val pointName: String
): Serializable
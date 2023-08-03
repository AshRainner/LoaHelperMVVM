package com.lostark.dto.armorys.tooltips


import com.google.gson.annotations.SerializedName

data class CardData(
    @SerializedName("awakeCount")
    val awakeCount: Int,
    @SerializedName("awakeTotal")
    val awakeTotal: Int,
    @SerializedName("cardStack")
    val cardStack: String,
    @SerializedName("iconData")
    val iconData: IconData,
    @SerializedName("isBookMark")
    val isBookMark: Boolean,
    @SerializedName("tierGrade")
    val tierGrade: Int
)
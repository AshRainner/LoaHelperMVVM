package com.lostark.loahelper.dto.armorys.tooltips


import com.google.gson.annotations.SerializedName

data class CardData(
    @SerializedName("awakeCount")
    val awakeCount: Int,
    @SerializedName("awakeTotal")
    val awakeTotal: Int,
    @SerializedName("cardStack")
    val cardStack: String,
    @SerializedName("iconData")
    val iconData: com.lostark.loahelper.dto.armorys.tooltips.IconData,
    @SerializedName("isBookMark")
    val isBookMark: Boolean,
    @SerializedName("tierGrade")
    val tierGrade: Int
)
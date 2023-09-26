package com.lostark.dto.auctions

import com.google.gson.annotations.SerializedName

data class AuctionsItem(
    @SerializedName("Name")
    val name:String,
    @SerializedName("Grade")
    val grade:String,
    @SerializedName("Level")
    val level:String?,
    @SerializedName("Icon")
    val iconUrl:String,
    @SerializedName("GradeQuality")
    val gradeQuality:Int?,
    @SerializedName("TradeRemainCount")
    val tradeRemainCount:String?,
    @SerializedName("YDayAvgPrice")
    val yDayAvgPrice:Double,
    @SerializedName("RecentPrice")
    val recentPrice:Int,
    @SerializedName("CurrentMinPrice")
    val currentMinPrice:Int
)

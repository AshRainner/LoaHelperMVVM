package com.lostark.loahelper.dto.markets

import com.google.gson.annotations.SerializedName

data class MarketsItem(
    @SerializedName("Id")
    val id:Int,
    @SerializedName("Name")
    val name:String,
    @SerializedName("Grade")
    val grade:String,
    @SerializedName("Icon")
    val iconUrl:String,
    @SerializedName("BundleCount")
    val bundleCount:Int,
    @SerializedName("TradeRemainCount")
    val tradeRemainCount:String?,
    @SerializedName("YDayAvgPrice")
    val yDayAvgPrice:Double,
    @SerializedName("RecentPrice")
    val recentPrice:Int,
    @SerializedName("CurrentMinPrice")
    val currentMinPrice:Int
)

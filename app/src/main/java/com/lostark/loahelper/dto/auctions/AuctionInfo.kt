package com.lostark.loahelper.dto.auctions

import com.google.gson.annotations.SerializedName

data class AuctionInfo(
    @SerializedName("StartPrice")
    val startPrice:Int,
    @SerializedName("BuyPrice")
    val buyPrice:Int?,
    @SerializedName("BidPrice")
    val bidPrice:Int,
    @SerializedName("EndDate")
    val endDate:String,
    @SerializedName("BidCount")
    val bidCount:Int,
    @SerializedName("BidStartPrice")
    val bidStartPrice:String,
    @SerializedName("IsCompetitive")
    val isCompetitive:Boolean,
    @SerializedName("TradeAllowCount")
    val tradeAllowCount:Int
)

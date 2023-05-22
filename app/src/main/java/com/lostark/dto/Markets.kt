package com.lostark.dto

import com.google.gson.annotations.SerializedName

data class MarketsBody(
    @SerializedName("Sort")
    val sort:String,
    @SerializedName("CategoryCode")
    val categoryCode:Int,//카테고리 코드
    @SerializedName("CharacterClass")
    val characterClass:String,
    @SerializedName("ItemTier")
    val itemTier:Int,
    @SerializedName("ItemGrade")
    val itemGrade:String,//아이템 희귀도
    @SerializedName("ItemName")
    val searchItemName:String,//서치할 아이템 이름
    @SerializedName("PageNo")
    val pageNo:Int,
    @SerializedName("SortCondition")
    val sortCondition:String
)
data class MarketsList(
    @SerializedName("PageNo")
    val pageNo:Int,
    @SerializedName("PageSize")
    val pageSize:Int,
    @SerializedName("TotalCount")
    val totalCount:Int,
    @SerializedName("Items")
    val items:MutableList<MarketsItem>,
)
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
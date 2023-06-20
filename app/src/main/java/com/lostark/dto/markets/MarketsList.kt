package com.lostark.dto.markets

import com.google.gson.annotations.SerializedName

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
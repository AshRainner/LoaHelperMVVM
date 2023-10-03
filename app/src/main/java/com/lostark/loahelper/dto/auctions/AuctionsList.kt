package com.lostark.loahelper.dto.auctions

import com.google.gson.annotations.SerializedName

data class AuctionsList(
    @SerializedName("PageNo")
    val pageNo:Int,
    @SerializedName("PageSize")
    val pageSize:Int,
    @SerializedName("TotalCount")
    val totalCount:Int,
    @SerializedName("Items")
    val items:MutableList<com.lostark.loahelper.dto.auctions.AuctionsItem>,
    @SerializedName("Options")
    val options: com.lostark.loahelper.dto.auctions.Options
)
package com.lostark.dto.auctions

import com.google.gson.annotations.SerializedName

data class AuctionsBody(
    @SerializedName("Sort")
    val sort:String,
    @SerializedName("CategoryCode")
    val categoryCode:Int,//카테고리 코드
    @SerializedName("CharacterClass")
    val characterClass:String?,
    @SerializedName("ItemTier")
    val itemTier:Int?,
    @SerializedName("ItemGrade")
    val itemGrade: String?,//아이템 희귀도
    @SerializedName("ItemName")
    val searchItemName:String?,//서치할 아이템 이름
    @SerializedName("PageNo")
    val pageNo:Int,
    @SerializedName("SortCondition")
    val sortCondition:String
)

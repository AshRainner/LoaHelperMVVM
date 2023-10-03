package com.lostark.loahelper.dto.news


import com.google.gson.annotations.SerializedName

data class EventItem(
    @SerializedName("EndDate")
    val endDate: String,
    @SerializedName("Link")
    val link: String,
    @SerializedName("RewardDate")
    val rewardDate: String,
    @SerializedName("StartDate")
    val startDate: String,
    @SerializedName("Thumbnail")
    val thumbnail: String,
    @SerializedName("Title")
    val title: String
)
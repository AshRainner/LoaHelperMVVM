package com.lostark.dto

import com.google.gson.annotations.SerializedName

data class EventDTO(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Thumbnail")
    val thumbnail: String,
    @SerializedName("Link")
    val link: String,
    @SerializedName("StartDate")
    val startDate: String,//date-time
    @SerializedName("EndDate")
    val endDate: String,//date-time
    @SerializedName("RewardDate")
    val rewardDate: String//date-time default: null
)

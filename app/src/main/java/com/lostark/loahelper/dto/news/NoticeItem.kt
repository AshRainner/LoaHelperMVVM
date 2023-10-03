package com.lostark.loahelper.dto.news


import com.google.gson.annotations.SerializedName

data class NoticeItem(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Link")
    val link: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String
)
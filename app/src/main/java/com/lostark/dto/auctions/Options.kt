package com.lostark.dto.auctions

import com.google.gson.annotations.SerializedName

data class Options(
    @SerializedName("Type")
    val type:String,
    @SerializedName("OptionName")
    val optionName:String,
    @SerializedName("OptionNameTripod")
    val optionNameTripod:String,
    @SerializedName("Value")
    val value:Int,
    @SerializedName("IsPenalty")
    val ispPenalty:Boolean,
    @SerializedName("ClassName")
    val className:String
)

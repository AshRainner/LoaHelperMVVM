package com.lostark.loahelper.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class ValueData<T>(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: T/*,
    @SerializedName("IsInner")
    val inner:Boolean,
    @SerializedName("IsSet")
    val isSet:Boolean*/
)
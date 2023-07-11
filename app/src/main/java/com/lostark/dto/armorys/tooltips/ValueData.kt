package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class ValueData<T>(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: T
)
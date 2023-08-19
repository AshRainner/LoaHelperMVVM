package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tooltip(
    @SerializedName("Elements")
    val elements: MutableMap<String,ValueData<*>>,
): Serializable

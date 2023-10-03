package com.lostark.loahelper.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tooltip(
    @SerializedName("Elements")
    val elements: MutableMap<String, com.lostark.loahelper.dto.armorys.tooltips.ValueData<*>>,
): Serializable

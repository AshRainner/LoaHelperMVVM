package com.lostark.loahelper.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class IndentStringElementData(
    @SerializedName("contentStr")
    val contentStrData: com.lostark.loahelper.dto.armorys.tooltips.ContentStrData,
    @SerializedName("topStr")
    val topStr:String
)

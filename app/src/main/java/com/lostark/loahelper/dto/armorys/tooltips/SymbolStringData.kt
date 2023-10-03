package com.lostark.loahelper.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class SymbolStringData(
    @SerializedName("contentStr")
    val contentStr:String,
    @SerializedName("titleStr")
    val titleStr:String
)

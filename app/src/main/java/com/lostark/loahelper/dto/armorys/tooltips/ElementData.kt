package com.lostark.loahelper.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class ElementData(

    @SerializedName("topStr")
    val topStr:String,
    @SerializedName("bPoint")
    val bPoint:Any,
    @SerializedName("contentStr")
    val contentStr:String,
    @SerializedName("desc")
    val desc:String,
    @SerializedName("lock")
    val lock:Boolean,
    @SerializedName("name")
    val name:String,
    @SerializedName("tier")
    val tier:String,
    @SerializedName("slotData")
    val slotData: com.lostark.loahelper.dto.armorys.tooltips.SlotData
)

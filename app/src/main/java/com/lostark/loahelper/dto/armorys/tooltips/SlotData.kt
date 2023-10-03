package com.lostark.loahelper.dto.armorys.tooltips


import com.google.gson.annotations.SerializedName

data class SlotData(
    @SerializedName("advBookIcon")
    val advBookIcon: Int,
    @SerializedName("battleItemTypeIcon")
    val battleItemTypeIcon: Int,
    @SerializedName("cardIcon")
    val cardIcon: Boolean,
    @SerializedName("friendship")
    val friendship: Int,
    @SerializedName("iconGrade")
    val iconGrade: Int,
    @SerializedName("iconPath")
    val iconPath: String,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("islandIcon")
    val islandIcon: Int,
    @SerializedName("rtString")
    val rtString: String,
    @SerializedName("seal")
    val seal: Boolean,
    @SerializedName("temporary")
    val temporary: Int,
    @SerializedName("town")
    val town: Int,
    @SerializedName("trash")
    val trash: Int
)
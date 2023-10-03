package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArmoryAvatar(
    @SerializedName("Grade")
    val grade: String,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("IsInner")
    val isInner: Boolean,
    @SerializedName("IsSet")
    val isSet: Boolean,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Tooltip")
    val tooltip: String,
    @SerializedName("Type")
    val type: String
): Serializable
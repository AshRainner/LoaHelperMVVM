package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rune(
    @SerializedName("Grade")
    val grade: String,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Tooltip")
    val tooltip: String
): Serializable
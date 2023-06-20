package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class EffectX(
    @SerializedName("Description")
    val description: String,
    @SerializedName("Name")
    val name: String
)
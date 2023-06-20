package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("Description")
    val description: String,
    @SerializedName("Name")
    val name: String
)
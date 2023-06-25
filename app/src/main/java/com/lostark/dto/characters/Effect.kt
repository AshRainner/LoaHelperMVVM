package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Effect(
    @SerializedName("CardSlots")
    val cardSlots: List<Int>,
    @SerializedName("Index")
    val index: Int,
    @SerializedName("Items")
    val items: List<Item>
): Serializable
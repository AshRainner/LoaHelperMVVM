package com.lostark.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Effect(
    @SerializedName("CardSlots")
    val cardSlots: List<Int>,
    @SerializedName("Index")
    val index: Int,
    @SerializedName("Items")
    val items: List<Item>,
    @SerializedName("Description")
    val description: String,
    @SerializedName("GemSlot")
    val gemSlot: Int,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Tooltip")
    val tooltip: String
): Serializable
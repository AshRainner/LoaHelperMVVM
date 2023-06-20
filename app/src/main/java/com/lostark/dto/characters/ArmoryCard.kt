package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class ArmoryCard(
    @SerializedName("Cards")
    val cards: List<Card>,
    @SerializedName("Effects")
    val effects: List<Effect>
)
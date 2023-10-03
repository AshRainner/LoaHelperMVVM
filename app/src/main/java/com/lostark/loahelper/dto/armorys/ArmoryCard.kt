package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArmoryCard(
    @SerializedName("Cards")
    val cards: List<com.lostark.loahelper.dto.armorys.Card>,
    @SerializedName("Effects")
    val effects: List<com.lostark.loahelper.dto.armorys.Effect>
): Serializable
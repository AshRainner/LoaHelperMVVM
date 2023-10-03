package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ColosseumInfo(
    @SerializedName("Colosseums")
    val colosseums: List<com.lostark.loahelper.dto.armorys.Colosseum>,
    @SerializedName("Exp")
    val exp: Int,
    @SerializedName("PreRank")
    val preRank: Int,
    @SerializedName("Rank")
    val rank: Int
): Serializable
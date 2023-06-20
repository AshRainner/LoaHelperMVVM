package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class ColosseumInfo(
    @SerializedName("Colosseums")
    val colosseums: List<Colosseum>,
    @SerializedName("Exp")
    val exp: Int,
    @SerializedName("PreRank")
    val preRank: Int,
    @SerializedName("Rank")
    val rank: Int
)
package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class Colosseum(
    @SerializedName("CoOpBattle")
    val coOpBattle: Any,
    @SerializedName("Competitive")
    val competitive: Any,
    @SerializedName("Deathmatch")
    val deathmatch: Any,
    @SerializedName("SeasonName")
    val seasonName: String,
    @SerializedName("TeamDeathmatch")
    val teamDeathmatch: TeamDeathmatch,
    @SerializedName("TeamElimination")
    val teamElimination: Any
)
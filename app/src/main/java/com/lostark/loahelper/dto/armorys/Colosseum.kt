package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    val teamDeathmatch: com.lostark.loahelper.dto.armorys.TeamDeathmatch,
    @SerializedName("TeamElimination")
    val teamElimination: Any
): Serializable
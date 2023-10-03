package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TeamDeathmatch(
    @SerializedName("AceCount")
    val aceCount: Int,
    @SerializedName("DeathCount")
    val deathCount: Int,
    @SerializedName("KillCount")
    val killCount: Int,
    @SerializedName("LoseCount")
    val loseCount: Int,
    @SerializedName("PlayCount")
    val playCount: Int,
    @SerializedName("TieCount")
    val tieCount: Int,
    @SerializedName("VictoryCount")
    val victoryCount: Int
): Serializable
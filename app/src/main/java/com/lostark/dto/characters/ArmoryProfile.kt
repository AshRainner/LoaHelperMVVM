package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArmoryProfile(
    @SerializedName("CharacterClassName")
    val characterClassName: String,
    @SerializedName("CharacterImage")
    val characterImage: String,
    @SerializedName("CharacterLevel")
    val characterLevel: Int,
    @SerializedName("CharacterName")
    val characterName: String,
    @SerializedName("ExpeditionLevel")
    val expeditionLevel: Int,
    @SerializedName("GuildMemberGrade")
    val guildMemberGrade: String,
    @SerializedName("GuildName")
    val guildName: String,
    @SerializedName("ItemAvgLevel")
    val itemAvgLevel: String,
    @SerializedName("ItemMaxLevel")
    val itemMaxLevel: String,
    @SerializedName("PvpGradeName")
    val pvpGradeName: String,
    @SerializedName("ServerName")
    val serverName: String,
    @SerializedName("Stats")
    val stats: List<Stat>,
    @SerializedName("Tendencies")
    val tendencies: List<Tendency>,
    @SerializedName("Title")
    val title: String,
    @SerializedName("TotalSkillPoint")
    val totalSkillPoint: Int,
    @SerializedName("TownLevel")
    val townLevel: Int,
    @SerializedName("TownName")
    val townName: String,
    @SerializedName("UsingSkillPoint")
    val usingSkillPoint: Int
): Serializable
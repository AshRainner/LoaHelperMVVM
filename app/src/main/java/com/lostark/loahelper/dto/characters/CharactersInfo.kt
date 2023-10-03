package com.lostark.loahelper.dto.characters


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharactersInfo(
    @SerializedName("CharacterClassName")
    val characterClassName: String,
    @SerializedName("CharacterLevel")
    val characterLevel: Int,
    @SerializedName("CharacterName")
    val characterName: String,
    @SerializedName("ItemAvgLevel")
    val itemAvgLevel: String,
    @SerializedName("ItemMaxLevel")
    val itemMaxLevel: String,
    @SerializedName("ServerName")
    val serverName: String
): Serializable
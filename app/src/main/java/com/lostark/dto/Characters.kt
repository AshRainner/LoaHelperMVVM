package com.lostark.dto

import com.google.gson.annotations.SerializedName

data class CharactersDTO(
    @SerializedName("ServerName")
    val server: String,
    @SerializedName("CharacterName")
    val characterName: String,
    @SerializedName("CharacterLevel")
    val characterLevel: Int,
    @SerializedName("CharacterClassName")
    val className: String,
    @SerializedName("ItemAvgLevel")
    val itemAvgLevel: String,
    @SerializedName("ItemMaxLevel")
    val itemMaxLevel: String
)
package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("ArmoryAvatars")
    val armoryAvatars: List<ArmoryAvatar>,
    @SerializedName("ArmoryCard")
    val armoryCard: ArmoryCard,
    @SerializedName("ArmoryEngraving")
    val armoryEngraving: ArmoryEngraving,
    @SerializedName("ArmoryEquipment")
    val armoryEquipment: List<ArmoryEquipment>,
    @SerializedName("ArmoryGem")
    val armoryGem: ArmoryGem,
    @SerializedName("ArmoryProfile")
    val armoryProfile: ArmoryProfile,
    @SerializedName("ArmorySkills")
    val armorySkills: List<ArmorySkill>,
    @SerializedName("Collectibles")
    val collectibles: List<Collectible>,
    @SerializedName("ColosseumInfo")
    val colosseumInfo: ColosseumInfo
)
package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Armories(
    @SerializedName("ArmoryAvatars")
    val armoryAvatars: List<com.lostark.loahelper.dto.armorys.ArmoryAvatar>,
    @SerializedName("ArmoryCard")
    val armoryCard: com.lostark.loahelper.dto.armorys.ArmoryCard,
    @SerializedName("ArmoryEngraving")
    val armoryEngraving: com.lostark.loahelper.dto.armorys.ArmoryEngraving,
    @SerializedName("ArmoryEquipment")
    val armoryEquipment: List<com.lostark.loahelper.dto.armorys.ArmoryEquipment>,
    @SerializedName("ArmoryGem")
    val armoryGem: com.lostark.loahelper.dto.armorys.ArmoryGem,
    @SerializedName("ArmoryProfile")
    val armoryProfile: com.lostark.loahelper.dto.armorys.ArmoryProfile,
    @SerializedName("ArmorySkills")
    val armorySkills: List<com.lostark.loahelper.dto.armorys.ArmorySkill>,
    @SerializedName("Collectibles")
    val collectibles: List<com.lostark.loahelper.dto.armorys.Collectible>,
    @SerializedName("ColosseumInfo")
    val colosseumInfo: com.lostark.loahelper.dto.armorys.ColosseumInfo
) : Serializable
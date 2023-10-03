package com.lostark.loahelper.api

import retrofit2.Call
import retrofit2.http.*

interface LostArkAPI {

    @GET("/news/events")
    fun getEvent(
        @Header("accept") accept: String,
        @Header("authorization") key: String?
    ):Call<MutableList<com.lostark.loahelper.dto.news.EventItem>>

    @GET("/news/notices")
    fun getNotice(
        @Header("accept") accept: String,
        @Header("authorization") key: String?
    ):Call<MutableList<com.lostark.loahelper.dto.news.NoticeItem>>

    @GET("/armories/characters/{characterName}")
    fun getArmories(
        @Header("accept") accept: String,
        @Header("authorization") key: String?,
        @Path("characterName") characterName: String
    ):Call<com.lostark.loahelper.dto.armorys.Armories>

    @POST("/markets/items")
    fun getItemsInfo(
        @Header("accept") accept: String,
        @Header("authorization") key: String?,
        @Header("Content-Type") contentType: String,
        @Body marketsBody: com.lostark.loahelper.dto.markets.MarketsBody
    ):Call<com.lostark.loahelper.dto.markets.MarketsList>

    @POST("/auctions/items")
    fun getAuctionItemsInfo(
        @Header("accept") accept: String,
        @Header("authorization") key: String?,
        @Header("Content-Type") contentType: String,
        @Body marketsBody: com.lostark.loahelper.dto.auctions.AuctionsBody
    ):Call<com.lostark.loahelper.dto.auctions.AuctionsList>

    @GET("/characters/{characterName}/siblings")
    fun getCharacters(
        @Header("accept") accept: String,
        @Header("authorization") key: String?,
        @Path("characterName") characterName: String
    ):Call<com.lostark.loahelper.dto.characters.Characters>
}
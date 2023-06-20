package com.lostark.api

import com.lostark.dto.characters.*
import com.lostark.dto.news.*
import com.lostark.dto.markets.*
import retrofit2.Call;
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LostArkAPI {

    @GET("/news/events")
    fun getEvent(
        @Header("accept") accept: String,
        @Header("authorization") key: String
    ):Call<MutableList<EventItem>>

    @GET("/news/notices")
    fun getNotice(
        @Header("accept") accept: String,
        @Header("authorization") key: String
    ):Call<MutableList<NoticeItem>>

    @GET("/armories/characters/{characterName}")
    fun getCharacters(
        @Header("accept") accept: String,
        @Header("authorization") key: String,
        @Path("characterName") characterName: String
    ):Call<Characters>

    @POST("/markets/items")
    fun getItemsInfo(
        @Header("accept") accept: String,
        @Header("authorization") key: String,
        @Header("Content-Type") contentType: String,
        @Body marketsBody: MarketsBody
    ):Call<MarketsList>
}
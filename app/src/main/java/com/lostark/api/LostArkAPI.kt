package com.lostark.api

import com.lostark.dto.*
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
    ):Call<MutableList<EventDTO>>

    @GET("/news/notices")
    fun getNotice(
        @Header("accept") accept: String,
        @Header("authorization") key: String
    ):Call<MutableList<NoticeDTO>>

    @GET("/news/{characterName}/siblings")
    fun getCharacters(
        @Header("accept") accept: String,
        @Header("authorization") key: String,
        @Path("characterName") characterName: String
    ):Call<MutableList<CharactersDTO>>

    @POST("/markets/items")
    fun getItemsInfo(
        @Header("accept") accept: String,
        @Header("authorization") key: String,
        @Header("Content-Type") contentType: String,
        @Body marketsBody: MarketsBody
    ):Call<MutableList<MarketsList>>
}
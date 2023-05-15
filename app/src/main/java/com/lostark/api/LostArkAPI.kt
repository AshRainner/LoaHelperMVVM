package com.lostark.api

import com.lostark.dto.EventDTO
import com.lostark.dto.NoticeDTO
import retrofit2.Call;
import retrofit2.http.GET
import retrofit2.http.Header

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
}
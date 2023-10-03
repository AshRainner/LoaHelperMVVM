package com.lostark.loahelper.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoaRetrofitObj {
    private const val BASE_URL = "https://developer-lostark.game.onstove.com"
    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
    fun getRetrofitService(): LostArkAPI {
        return getRetrofit().create(LostArkAPI::class.java)
    }
}
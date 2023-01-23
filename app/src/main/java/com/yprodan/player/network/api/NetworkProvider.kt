package com.yprodan.player.network.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object NetworkProvider {

    fun <T> provideApiService(

        gson: Gson,
        baseUrl: String,
        service: Class<T>
    ): T {
        return provideRetrofitBuilder(gson)
            .baseUrl(baseUrl).build().create(service)
    }

    private fun provideRetrofitBuilder(
        gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    fun provideGson(): Gson = GsonBuilder().setLenient().create()
}
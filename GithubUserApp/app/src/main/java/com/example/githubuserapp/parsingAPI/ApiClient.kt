package com.example.githubuserapp.parsingAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val apiService: ApiService get() {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(" https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
}
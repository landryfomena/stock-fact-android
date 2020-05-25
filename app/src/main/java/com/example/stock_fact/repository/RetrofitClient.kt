package com.example.stock_fact.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by netserve on 13/10/2017.
 */
object RetrofitClient {
    private var retrofit: Retrofit? = null
    private val TAG = RetrofitClient::class.java.simpleName
    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
    fun getClient(url: String?): Retrofit? {
        val builder = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
        retrofit = builder.build()
        return retrofit
    }
}

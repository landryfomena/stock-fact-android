package com.example.stock_fact.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by netserve on 13/10/2017.
 */
object RetrofitClient {
    private var retrofit: Retrofit? = null
    private val TAG = RetrofitClient::class.java.simpleName
    fun getClient(url: String?): Retrofit? {
        val builder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
        retrofit = builder.build()
        return retrofit
    }
}

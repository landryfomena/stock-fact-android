package com.example.stock_fact.repository

import com.example.stock_fact.domain.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface SprintPayServicesRemote {
    @POST("auth/login")
    fun login(@Header("Authorization") authHeader: String?): Call<User?>?

    @POST("/product/register")
    fun createProduct(@Header("Authorization") authHeader: String?, @Body product: Product?): Call<ProductResponse?>?

    @GET("/product/products")
    fun getProducts(@Header("Authorization") authHeader: String?): Call<List<ProductResponse?>?>
    @GET("/product/products")
    fun listRepos(@Header("Authorization") user: String?): Call<List<ProductResponse?>?>?
    @POST("/command/register")
    fun createCommand(@Header("Authorization") authHeader: String?, @Body command: CommanRequest?): Call<Command?>?
}

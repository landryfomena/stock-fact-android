package com.example.stock_fact.repository

import com.example.stock_fact.domain.*
import retrofit2.Call
import retrofit2.http.*


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

    @POST("/auth/register")
    fun createUser(@Header("Authorization") authHeader: String?, @Body userRequest: UserRequest?): Call<User?>?

    @POST("/auth/validate")
    fun validateUser(@Header("Authorization") authHeader: String?,@Query("validateMessage") validateMessage: Int,@Query("username") username: String): Call<User?>?
}

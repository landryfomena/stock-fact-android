package com.example.stock_fact.repository

import com.example.stock_fact.domain.Product
import com.example.stock_fact.domain.ProductResponse
import com.example.stock_fact.domain.User
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
    fun getProducts(@Header("Authorization") authHeader: String?): Call<List<ProductResponse>?>
}

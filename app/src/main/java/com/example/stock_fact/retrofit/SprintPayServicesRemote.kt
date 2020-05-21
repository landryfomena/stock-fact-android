package com.example.stock_fact.retrofit

import com.example.stock_fact.domain.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface SprintPayServicesRemote {
    @POST("auth/login")
    fun login(@Header("Authorization") authHeader: String?): Call<User?>?

    //@POST("/user/creer_caisse")
    //fun creerCaisse(@Header("Authorization") authHeader: String?, @Body caissePojo: CaissePojo?): Call<Caisse?>?

   // @GET("/caisse/getCaisses")
  //  fun getCaisses(@Header("Authorization") authHeader: String?): Call<List<Caisse?>?>?
}

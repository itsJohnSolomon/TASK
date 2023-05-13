package com.jojo.cloudintask.retrofit

import com.jojo.cloudintask.models.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersAPI {

    @GET("api")
    suspend fun getUsers(@Query("page") page: Int,@Query("results") results: Int): UserModel
}
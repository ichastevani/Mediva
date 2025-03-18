package com.example.myapplication.network

import com.example.myapplication.network.request.RequestLogin
import com.example.myapplication.network.request.RequestRegister
import com.example.myapplication.network.response.ResponseIsRegistered
import com.example.myapplication.network.response.ResponseLogin

import com.example.myapplication.network.response.ResponseMessage
import com.example.myapplication.network.response.ResponseTransaction
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PMRApiService {
  @GET("open/users/{address}/check")
  suspend fun getIsUserRegistered(@Path("address") address: String):
    ResponseMessage<ResponseIsRegistered>

  // Auth
  @POST("auth/register")
  suspend fun register(
    @Body request: RequestRegister,
  ): ResponseMessage<ResponseTransaction>

  @POST("auth/login")
  suspend fun login(
    @Body request: RequestLogin,
  ): ResponseMessage<ResponseLogin>

  // Health Records
  @POST("health-records")
  suspend fun postHealthRecords(
    @Body request: RequestRegister,
  ): ResponseMessage<ResponseTransaction>
}
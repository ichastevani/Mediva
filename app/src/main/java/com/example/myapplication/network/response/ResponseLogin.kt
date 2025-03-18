package com.example.myapplication.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLogin(
  val token: String,
  val user: UserDataLogin,
)

@Serializable
data class UserDataLogin(
  val name: String,
  val birthDate: String,
  val homeAddress: String,
  val role: String,
  val address: String,
)
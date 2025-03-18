package com.example.myapplication.network.request

data class RequestRegister(
  val address: String,
  val name: String,
  val birthDate: String,
  val homeAddress: String,
  val role: String,
  val password: String
)


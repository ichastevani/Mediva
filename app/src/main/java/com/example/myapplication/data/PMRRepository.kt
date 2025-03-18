package com.example.myapplication.data

import android.R.attr.password
import android.util.Log
import coil.util.CoilUtils.result
import com.example.myapplication.network.PMRApiService
import com.example.myapplication.network.request.RequestLogin
import com.example.myapplication.network.request.RequestRegister
import com.example.myapplication.network.response.ResponseLogin
import com.example.myapplication.network.response.ResponseMessage
import com.example.myapplication.network.response.ResponseTransaction

interface PMRRepository {
  suspend fun getIsUserRegistered(address: String): Boolean

  suspend fun register(
    requestRegister: RequestRegister
  ): ResponseMessage<ResponseTransaction?>

  suspend fun login(
    requestLogin: RequestLogin
  ): ResponseMessage<ResponseLogin?>

}

class NetworkPMRRepository(
  private val pmrApiService: PMRApiService
) : PMRRepository {

  override suspend fun getIsUserRegistered(address: String): Boolean {
    val result = runCatching { pmrApiService.getIsUserRegistered(address) }
      .getOrElse {
        return false
      }
    return result.data?.is_registered == true
  }

  override suspend fun register(
    requestRegister: RequestRegister
  ): ResponseMessage<ResponseTransaction?> {
    // Log.d("NetworkPMRRepository", "data: ${requestRegister}")
    return try {
      val result = pmrApiService.register(requestRegister)
      // Log.d("NetworkPMRRepository", "register: ${result}")
      result
    } catch (e: Exception) {
      // Log.d("NetworkPMRRepository", e.message ?: "Unknown error")
      ResponseMessage(status = "error", message = e.message ?: "Unknown error")
    } as ResponseMessage<ResponseTransaction?>
  }

  override suspend fun login(requestLogin: RequestLogin): ResponseMessage<ResponseLogin?> {
    return try {
      val result = pmrApiService.login(requestLogin)
      result
    } catch (e: Exception) {
      ResponseMessage(status = "error", message = e.message ?: "Unknown error")
    } as ResponseMessage<ResponseLogin?>
  }
}
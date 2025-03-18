package com.example.myapplication.ui.viewModels

import android.R.attr.password
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.PMRRepository
import com.example.myapplication.network.request.RequestLogin
import com.example.myapplication.network.request.RequestRegister
import com.example.myapplication.network.response.UserDataLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PMRIsRegisteredUiState {
  data class Success(val isRegistered: Boolean) : PMRIsRegisteredUiState
  data class Error(val message: String) : PMRIsRegisteredUiState
  object Loading : PMRIsRegisteredUiState
}

sealed interface PMRTransactionUiState {
  data class Success(val transaction: String?) : PMRTransactionUiState
  data class Error(val message: String) : PMRTransactionUiState
  object Loading : PMRTransactionUiState
}

sealed interface PMRLoginUiState {
  data class Success(val token: String, val user: UserDataLogin) : PMRLoginUiState
  data class Error(val message: String) : PMRLoginUiState
  object Loading : PMRLoginUiState
}

data class UiStatePMR(
  val isRegistered: PMRIsRegisteredUiState = PMRIsRegisteredUiState.Loading,
  val transaction: PMRTransactionUiState = PMRTransactionUiState.Loading,
  val login: PMRLoginUiState = PMRLoginUiState.Loading,
)

@HiltViewModel
class PMRViewModel @Inject constructor(private val pmrRepository: PMRRepository) : ViewModel() {
  private val _uiState = MutableStateFlow(UiStatePMR())
  val uiState = _uiState.asStateFlow()

  fun getIsUserRegistered(address: String) {
    _uiState.update {
      it.copy(
        isRegistered = PMRIsRegisteredUiState.Loading,
      )
    }

    viewModelScope.launch {
      _uiState.update {
        val isRegisteredState = runCatching {
          pmrRepository.getIsUserRegistered(address)
        }.fold(
          onSuccess = { PMRIsRegisteredUiState.Success(it) },
          onFailure = { PMRIsRegisteredUiState.Error("Pengguna belum terdaftar") }
        )

        it.copy(
          isRegistered = isRegisteredState
        )
      }

      _uiState.value = _uiState.value.apply {

      }
    }
  }

  fun register(requestRegister: RequestRegister) {
    viewModelScope.launch {
      _uiState.update {
        it.copy(
          transaction = PMRTransactionUiState.Loading
        )
      }

      _uiState.update {
        val transactionState = runCatching {
          pmrRepository.register(requestRegister)
        }.fold(
          onSuccess = { PMRTransactionUiState.Success(it.data?.transactionData) },
          onFailure = { PMRTransactionUiState.Error(it.message ?: "Unknown error") }
        )

        it.copy(
          transaction = transactionState
        )
      }
    }
  }

  fun login(requestLogin: RequestLogin) {
    viewModelScope.launch {
      _uiState.update {
        it.copy(
          login = PMRLoginUiState.Loading
        )
      }

      _uiState.update {
        val loginState = runCatching {
          pmrRepository.login(requestLogin)
        }.fold(
          onSuccess = {
            if(it.data?.token == null){
              PMRLoginUiState.Error("Data token dan user tidak tersedia")
            }else{
              PMRLoginUiState.Success(it.data.token, it.data.user)
            }
          },
          onFailure = { PMRLoginUiState.Error(it.message ?: "Unknown error") }
        )

        it.copy(
          login = loginState
        )
      }
    }
  }
}
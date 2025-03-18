package com.example.myapplication.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.helper.OnEvent
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.PMRScreenEnum
import com.example.myapplication.ui.viewModels.EventSinkMetaMask
import com.example.myapplication.ui.viewModels.PMRIsRegisteredUiState
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRViewModel
import com.example.myapplication.ui.viewModels.UiEventMetaMask

@Composable
fun MetaMaskConnectScreen(
  navController: NavHostController,
  metaMaskViewModel: MetaMaskViewModel,
  pmrViewModel: PMRViewModel,
  snackbarHost: SnackbarHostState
) {
  val context = LocalContext.current
  val uiStateMetaMask by metaMaskViewModel.uiState.collectAsState()
  val uiStatePMR by pmrViewModel.uiState.collectAsState()

  LaunchedEffect(key1 = uiStateMetaMask.isConnecting) {
    if (uiStateMetaMask.isConnecting) {
      val ethAddress = uiStateMetaMask.ethAddress
      if(ethAddress != null){
        pmrViewModel.getIsUserRegistered(ethAddress)
      }else{
        snackbarHost.showSnackbar(
          message = "Gagal terkoneksi dengan MetaMask",
          duration = SnackbarDuration.Long
        )
      }
    }
  }

  LaunchedEffect(key1 = uiStatePMR.isRegistered) {
    val ethAddress = uiStateMetaMask.ethAddress
    if(ethAddress != null){
      when(uiStatePMR.isRegistered){
        is PMRIsRegisteredUiState.Loading -> {}
        is PMRIsRegisteredUiState.Error -> {
          snackbarHost.showSnackbar(
            message = "Gagal terhubung dengan server",
            duration = SnackbarDuration.Long
          )
        }
        is PMRIsRegisteredUiState.Success -> {
          val isRegistered = (uiStatePMR.isRegistered as PMRIsRegisteredUiState.Success).isRegistered
          Log.d("MetaMaskConnectScreen", "isRegistered: ${isRegistered}")
          if(isRegistered){
            navController.navigate(PMRScreenEnum.Login.name)
          }else{
            navController.navigate(PMRScreenEnum.Register.name)
          }
        }
      }
    }
  }

  OnEvent(events = metaMaskViewModel.uiEvent) { event ->
    when (event) {
      is UiEventMetaMask.Message -> {
        Toast.makeText(
          context,
          event.error,
          Toast.LENGTH_SHORT
        ).show()
      }
    }
  }

  Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        Icons.Filled.AccountCircle,
        contentDescription = "Wallet Icon",
        modifier = Modifier.size(100.dp),
        tint = Color(0xFF3F51B5)
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        "Your Wallet",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF3F51B5)
      )
      Spacer(modifier = Modifier.height(8.dp))
      if (uiStateMetaMask.isConnecting) {

      } else {
        Button(
          onClick = { metaMaskViewModel.eventSink(EventSinkMetaMask.Connect) },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
          Text("Connect", color = Color.White)
        }
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
private fun LoggedInPreview() {
  MyApplicationTheme {
  }
}
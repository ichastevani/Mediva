package com.example.myapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.ActivityScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.MetaMaskConnectScreen
import com.example.myapplication.ui.screens.PermissionScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.RecordScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRViewModel


enum class PMRScreenEnum(val title: String) {
  Welcome(title = "Welcome"),
  Login(title = "Login"),
  Register(title = "Register"),
  Home(title = "Home"),
  Record(title = "Record"),
  Profile(title = "Profile"),
  Permission(title = "Permission"),
  Activity(title = "Activity")
}


@Composable
fun PMRApp (
  metaMaskViewModel: MetaMaskViewModel,
  pmrViewModel: PMRViewModel,
  navController: NavHostController = rememberNavController(),
) {
  // Inisialisasi SnackbarHostState
  val snackbarHostState = remember { SnackbarHostState() }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) }
  ){ innerPadding ->
    NavHost(
      navController = navController,
      startDestination = PMRScreenEnum.Welcome.name,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPadding)
    ) {
      composable(route = PMRScreenEnum.Welcome.name) {
        MetaMaskConnectScreen(
          navController,
          metaMaskViewModel = metaMaskViewModel,
          pmrViewModel = pmrViewModel,
          snackbarHost = snackbarHostState,
        )
      }
      composable(
        route = PMRScreenEnum.Login.name,
        // arguments = listOf(navArgument("ethAddress") { type = NavType.StringType })
      ) { backStackEntry ->
        // val ethAddress = backStackEntry.arguments?.getString("ethAddress") ?: ""
        LoginScreen(
          navController,
          metaMaskViewModel,
          pmrViewModel,
          snackbarHost = snackbarHostState
        )
      }
      composable(
        route = PMRScreenEnum.Register.name,
        // arguments = listOf(navArgument("ethAddress") { type = NavType.StringType })
      ) { backStackEntry ->
        // val ethAddress = backStackEntry.arguments?.getString("ethAddress") ?: ""
        RegisterScreen(
          navController,
          metaMaskViewModel,
          pmrViewModel,
          snackbarHost = snackbarHostState,
        )
      }
      composable(
        route = PMRScreenEnum.Home.name,
      ) { backStackEntry ->
        HomeScreen(
          navController,
          metaMaskViewModel,
          pmrViewModel,
          snackbarHost = snackbarHostState,
        )
      }

      composable(route = PMRScreenEnum.Record.name) {
        RecordScreen(navController,
          metaMaskViewModel,
          pmrViewModel,
          snackbarHost = snackbarHostState,)
      }
      composable(route = PMRScreenEnum.Profile.name) {
        ProfileScreen(navController,
          metaMaskViewModel,
          pmrViewModel,
          snackbarHost = snackbarHostState,)
      }
      composable(route = PMRScreenEnum.Permission.name) {
        PermissionScreen(navController,
          metaMaskViewModel,
          pmrViewModel,
          snackbarHost = snackbarHostState,)
      }
      composable(route = PMRScreenEnum.Activity.name) {
        ActivityScreen(navController,
          metaMaskViewModel,
          pmrViewModel,
          snackbarHost = snackbarHostState,)
      }

    }
  }
}
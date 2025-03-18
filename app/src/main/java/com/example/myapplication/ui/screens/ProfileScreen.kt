package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.network.response.UserDataLogin
import com.example.myapplication.ui.PMRScreenEnum
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRLoginUiState
import com.example.myapplication.ui.viewModels.PMRViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    metaMaskViewModel: MetaMaskViewModel,
    pmrViewModel: PMRViewModel,
    snackbarHost: SnackbarHostState
) {
    var user by remember { mutableStateOf<UserDataLogin?>(null) }
    val uiStatePMR by pmrViewModel.uiState.collectAsState()
    val uiStateMetaMask by metaMaskViewModel.uiState.collectAsState()

    val ethAddress = uiStateMetaMask.ethAddress
    if (ethAddress == null) {
        navController.navigate(PMRScreenEnum.Welcome.name)
        return
    }

    LaunchedEffect(key1 = uiStatePMR.login) {
        when (uiStatePMR.login) {
            is PMRLoginUiState.Success -> {
                user = (uiStatePMR.login as PMRLoginUiState.Success).user
            }

            else -> {
                navController.navigate(PMRScreenEnum.Welcome.name)
            }

        }
    }

    if(user == null) return;

    ProfileUI(
        name = user!!.name,
        birthDate = user!!.birthDate,
        homeAddress = user!!.homeAddress,
        address = user!!.address,
        role = user!!.role,
        navController = navController
    )
}

@Composable
fun ProfileUI(
    navController: NavHostController,
    name: String,
    birthDate: String,
    homeAddress: String,
    address: String,
    role: String
) {
    Column(modifier = Modifier.padding(0.dp).background(Color.White)) {
        // Top Bar
        TopAppBarUI()

        // Profile Information
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            ProfileDetailRow("Name", name)
            ProfileDetailRow("Birthdate", birthDate)
            ProfileDetailRow("Home Address", homeAddress)
            ProfileDetailRow("Address", address)
            ProfileDetailRow("Role", role)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Navigation Bar
        BottomNavigationBarUI(navController)
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    // Using plain Text with white background and padding instead of a Card
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = Color.Black
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    MyApplicationTheme {
        ProfileUI(
            navController = rememberNavController(),
            name = "icha",
            birthDate = "2002/10/07",
            homeAddress = "Indonesia",
            address = "0x9dE3014241ee7df66A091A0fF3081deB278e8006",
            role = "Patient"
        )
    }
}

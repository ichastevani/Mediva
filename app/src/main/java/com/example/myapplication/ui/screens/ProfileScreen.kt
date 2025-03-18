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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    metaMaskViewModel: MetaMaskViewModel,
    pmrViewModel: PMRViewModel,
    snackbarHost: SnackbarHostState
) {
    ProfileUI(
        name = "icha",
        birthDate = "2002/10/07",
        homeAddress = "Indonesia",
        address = "0x9dE3014241ee7df66A091A0fF3081deB278e8006",
        password = "icha",
        role = "Patient",
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
    password: String,
    role: String
) {
    Column(modifier = Modifier.padding(0.dp)) {
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
            password = "icha",
            role = "Patient"
        )
    }
}

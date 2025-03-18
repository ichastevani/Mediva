package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRViewModel

data class Notification(
    val name: String,
    val action: String,
    val time: String
)

data class AccessHistory(
    val name: String,
    val action: String,
    val time: String
)

val notificationList = listOf(
    Notification(name = "Dr. John Doe", action = "Access to your Vaccination Records", time = "04:32 PM"),
    Notification(name = "Dr. Alex", action = "Access to your Vaccination Records", time = "04:32 PM")
)

val accessHistoryList = listOf(
    AccessHistory(name = "Dr. John Doe", action = "Requested access to your Vaccination Records", time = "04:32 PM"),
    AccessHistory(name = "Dr. Alex", action = "Requested access to your Vaccination Records", time = "04:32 PM")
)


@Composable
fun PermissionScreen(
    navController: NavHostController,
    metaMaskViewModel: MetaMaskViewModel,
    pmrViewModel: PMRViewModel,
    snackbarHost: SnackbarHostState
) {
    PermissionUI(navController)
}

@Composable
fun PermissionUI(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(1) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        TopAppBarUI()

        Box(
            modifier = Modifier
                .weight(1f) // Mendorong konten agar BottomNavigation menempel di bawah
                .fillMaxWidth(),
//            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                // Tab Row for Notification and Access History
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Notification") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Access History") }
                    )
                }

                // Content based on selected tab
                when (selectedTab) {
                    0 -> NotificationTab(notificationList)
                    1 -> AccessHistoryTab(accessHistoryList)
                }
            }
        }

        BottomNavigationBarUI(navController)
    }
}

@Composable
fun NotificationTab(notificationList: List<Notification>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        notificationList.forEach { notification ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = notification.name)
                    Text(text = notification.action)
                    Text(text = notification.time)
                    Button(
                        onClick = { /* Handle Revoke Access action */ },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Revoke Access")
                    }
                }
            }
        }
    }
}

@Composable
fun AccessHistoryTab(accessHistoryList: List<AccessHistory>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
    ) {
        accessHistoryList.forEach { history ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = history.name)
                    Text(text = history.action)
                    Text(text = history.time)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { /* Handle Approve View action */ }
                        ) {
                            Text("Approve View")
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { /* Handle Approve Edit action */ }
                        ) {
                            Text("Approve Edit")
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { /* Handle Reject action */ }
                        ) {
                            Text("Reject")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPermissionScreen() {
    MyApplicationTheme {
        PermissionUI(navController = rememberNavController())
    }
}

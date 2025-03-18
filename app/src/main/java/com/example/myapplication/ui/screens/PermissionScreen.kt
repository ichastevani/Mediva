package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
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
    val action: String,   // Action description (e.g., "Access", "Requested", etc.)
    val time: String,     // Time of action
    val actionTypes: List<String> // List of action types (e.g., ["Read", "Write"])
)

val notificationList = listOf(
    Notification(name = "Dr. John Doe", action = "Access to your Vaccination Records", time = "04:32 PM"),
    Notification(name = "Dr. Alex", action = "Access to your Vaccination Records", time = "04:32 PM")
)

val accessHistoryList = listOf(
    AccessHistory(
        name = "Dr. John Doe",
        action = "Requested access",
        time = "04:32 PM",
        actionTypes = listOf("Read")
    ),
    AccessHistory(
        name = "Dr. Alex",
        action = "Requested access",
        time = "04:35 PM",
        actionTypes = listOf("Write")
    ),
    AccessHistory(
        name = "Dr. Mike",
        action = "Requested access",
        time = "05:00 PM",
        actionTypes = listOf("Update", "Write")
    ),
    AccessHistory(
        name = "Dr. Sarah",
        action = "Requested access",
        time = "06:15 PM",
        actionTypes = listOf("Delete", "Read")
    )
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
                .fillMaxWidth()
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

                Box(modifier = Modifier.verticalScroll(rememberScrollState())){
                    when (selectedTab) {
                        0 -> NotificationTab(notificationList)
                        1 -> AccessHistoryTab(accessHistoryList)
                    }
                }
                // Content based on selected tab

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
                    Text(
                        text = "${history.action} ",
                        color = Color.Black // Default color for the action
                    )
                    // Style actionTypes (blue, italic)
                    Text(
                        text = "${history.actionTypes.joinToString(", ")}", // Display action types in one line
                        color = Color.Blue, // Blue color
                        fontStyle = FontStyle.Italic, // Italic style
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(text = history.time)

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        ApproveButton()
                        RejectButton()
                    }
                }
            }
        }
    }
}


@Composable
fun RejectButton() {
    Button(
        onClick = { /* Handle Reject action */ },
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(50), // Rounded corners
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red, // Always red for Reject button
            contentColor = Color.White // White text for both buttons
        )
    ) {
        Text("Reject")
    }
}


@Composable
fun ApproveButton() {
    Button(
        onClick = { /* Handle Approve action */ },
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(50), // Rounded corners
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0069C0), // Blue background for Approve
            contentColor = Color.White // White text for both buttons
        )
    ) {
        Text("Approve")
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewPermissionScreen() {
    MyApplicationTheme {
        PermissionUI(navController = rememberNavController())
    }
}

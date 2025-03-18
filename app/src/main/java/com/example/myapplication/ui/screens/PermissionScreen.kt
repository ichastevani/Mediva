package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    var selectedTab by remember { mutableStateOf(1) } // Default to Access History tab
    var showRejectDialog by remember { mutableStateOf(false) } // State for showing the reject dialog
    var showApproveDialog by remember { mutableStateOf(false) } // State for showing the approve dialog
    var showRevokeDialog by remember { mutableStateOf(false) } // State for showing the revoke dialog
    var selectedName by remember { mutableStateOf("") } // Store the name to show in dialog

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Top Bar
        TopAppBarUI()

        Box(
            modifier = Modifier
                .weight(1f) // Mendorong konten agar BottomNavigation menempel di bawah
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {

                // Tab Row for Notification and Access History

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White) // Set the background color to white
                )
                {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        modifier = Modifier.fillMaxWidth().background(Color.White)
                    ) {
                        Tab(
                            modifier = Modifier.fillMaxWidth().background(Color.White),
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Notification") }
                        )
                        Tab(
                            modifier = Modifier.fillMaxWidth().background(Color.White),
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Access History") }
                        )
                    }
                }

                // Content based on selected tab
                when (selectedTab) {
                    0 -> NotificationTab(notificationList, onRejectClicked = { name ->
                        selectedName = name
                        showRejectDialog = true
                    }, onRevokeClicked = { name ->
                        selectedName = name
                        showRevokeDialog = true // Show the revoke confirmation dialog
                    })
                    1 -> AccessHistoryTab(accessHistoryList, onRejectClicked = { name ->
                        selectedName = name
                        showRejectDialog = true
                    }, onApproveClicked = { name ->
                        selectedName = name
                        showApproveDialog = true
                    })
                }
            }
        }

        // Bottom Navigation Bar
        BottomNavigationBarUI(navController)
    }

    // Show confirmation dialog for reject
    if (showRejectDialog) {
        RejectConfirmationDialog(
            showDialog = showRejectDialog,
            onDismiss = { showRejectDialog = false },
            onConfirm = {
                // Handle rejection logic here (e.g., revoke access)
                println("Rejected access for $selectedName")
                showRejectDialog = false // Close the dialog after confirmation
            },
            name = selectedName
        )
    }

    // Show confirmation dialog for approve
    if (showApproveDialog) {
        ApproveConfirmationDialog(
            showDialog = showApproveDialog,
            onDismiss = { showApproveDialog = false },
            onConfirm = {
                // Handle approval logic here (e.g., approve access)
                println("Approved access for $selectedName")
                showApproveDialog = false // Close the dialog after confirmation
            },
            name = selectedName
        )
    }

    // Show confirmation dialog for revoke
    if (showRevokeDialog) {
        RevokeConfirmationDialog(
            showDialog = showRevokeDialog,
            onDismiss = { showRevokeDialog = false },
            onConfirm = {
                // Handle revoking access logic here
                println("Revoked access for $selectedName")
                showRevokeDialog = false // Close the dialog after confirmation
            },
            name = selectedName
        )
    }
}

@Composable
fun RevokeConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    name: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Are you sure you want to revoke?")
            },
            text = {
                Text("Are you sure you want to revoke access for $name? This action cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm() // Confirm revoke
                        onDismiss() // Dismiss the dialog
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0069C0), // Blue background for Yes button
                        contentColor = Color.White // White text for Yes button
                    ),
                    shape = RoundedCornerShape(50) // Rounded corners for the button
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss, // Dismiss the dialog without doing anything
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, // Red background for No button
                        contentColor = Color.White
                    ),
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun ApproveConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    name: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Are you sure you want to approve?")
            },
            text = {
                Text("Are you sure you want to approve access for $name?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm() // Confirm approval
                        onDismiss() // Dismiss the dialog
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0069C0), // Blue background for Yes button
                        contentColor = Color.White // White text for Yes button
                    ),
                    shape = RoundedCornerShape(50) // Rounded corners for the button
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss, // Dismiss the dialog without doing anything
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, // Red background for No button
                        contentColor = Color.White // White text for No button
                    ),
                    shape = RoundedCornerShape(50) // Rounded corners for the button
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun RejectConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    name: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Are you sure you want to reject?")
            },
            text = {
                Text("Are you sure you want to reject access for $name? This action cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm() // Confirm rejection
                        onDismiss() // Dismiss the dialog
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0069C0), // Blue background for Yes button
                        contentColor = Color.White // White text for Yes button
                    ),
                    shape = RoundedCornerShape(50) // Rounded corners for the button
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss, // Dismiss the dialog without doing anything
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, // Red background for No button
                        contentColor = Color.White // White text for No button
                    ),
                    shape = RoundedCornerShape(50) // Rounded corners for the button
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun NotificationTab(notificationList: List<Notification>, onRejectClicked: (String) -> Unit, onRevokeClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        notificationList.forEach { notification ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE1E2EC))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = notification.name)
                    Text(text = notification.action)
                    Text(text = notification.time)
                    Button(
                        onClick = { onRevokeClicked(notification.name) }, // Trigger revoke dialog
                        modifier = Modifier.padding(top = 8.dp),
                        shape = RoundedCornerShape(50), // Rounded corners
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red, // Blue background for Approve
                            contentColor = Color.White // White text for both buttons
                        )
                    ) {
                        Text("Revoke Access")
                    }
                }
            }
        }
    }
}

@Composable
fun AccessHistoryTab(accessHistoryList: List<AccessHistory>, onRejectClicked: (String) -> Unit, onApproveClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        accessHistoryList.forEach { history ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE1E2EC))
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
                        ApproveButton(onApproveClicked = { onApproveClicked(history.name) })
                        RejectButton(onRejectClicked = { onRejectClicked(history.name) })
                    }
                }
            }
        }
    }
}

@Composable
fun RejectButton(onRejectClicked: (String) -> Unit) {
    Button(
        onClick = { onRejectClicked("Dr. John Doe") }, // Replace with the actual name of the person to be rejected
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
fun ApproveButton(onApproveClicked: (String) -> Unit) {
    Button(
        onClick = { onApproveClicked("Dr. John Doe") }, // Replace with the actual name of the person to be approved
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

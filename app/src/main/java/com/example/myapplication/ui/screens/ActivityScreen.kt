package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRViewModel

data class Activity(
    val timestamp: String,
    val activityType: String,
    val module: String,
    val performedBy: String
)

val activityList = listOf(
    Activity(
        timestamp = "10 March 2023, 14:00",
        activityType = "Access Request Submitted",
        module = "Vaccination Records",
        performedBy = "Dr. John Doe"
    ),
    Activity(
        timestamp = "09 March 2023, 12:30",
        activityType = "Access Granted",
        module = "Radiology Reports",
        performedBy = "Dr. Jane Smith"
    )
)

@Composable
fun ActivityScreen(
    navController: NavHostController,
    metaMaskViewModel: MetaMaskViewModel,
    pmrViewModel: PMRViewModel,
    snackbarHost: SnackbarHostState
) {
    ActivityUI(navController)
}

@Composable
fun ActivityUI(navController: NavHostController) {

    Column {
        // Top Bar
        TopAppBarUI()

        Box(
            modifier = Modifier
                .weight(1f) // Mendorong konten agar BottomNavigation menempel di bawah
                .fillMaxWidth()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier.padding(0.dp) .background(Color.White)
            ) {
                // Calling the ActivityTable composable here
                ActivityTable(activityList = activityList)
            }
        }

        BottomNavigationBarUI(navController)

    }
}

@Composable
fun ActivityTable(activityList: List<Activity>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Table Header
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Timestamp",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Activity Type",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Module",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Performed By",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Table Content
        activityList.forEach { activity ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = activity.timestamp,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = activity.activityType,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = activity.module,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = activity.performedBy,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewActivityScreen() {
    MyApplicationTheme {
        ActivityUI(navController = rememberNavController())
    }
}

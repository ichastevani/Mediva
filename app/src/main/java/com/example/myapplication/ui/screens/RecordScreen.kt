package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRViewModel

data class MedicalRecord(
    val title: String,
    val description: String,
    val icon: Int // This should be a reference to a drawable resource ID
)
@Composable
fun RecordScreen(
    navController: NavHostController,
    metaMaskViewModel: MetaMaskViewModel,
    pmrViewModel: PMRViewModel,
    snackbarHost: SnackbarHostState
) {
    RecordUI(navController)
}

@Composable
fun RecordUI(navController: NavHostController) {
    val medicalRecords = listOf(
        MedicalRecord("Vaccination Records", "List of uploaded vaccination records", R.drawable.ic_broken_image),
        MedicalRecord("Radiology Reports", "Stores imaging results (e.g., X-rays, MRIs, CT scans)", R.drawable.ic_connection_error),
        MedicalRecord("Diagnostic Tests", "Stores lab test results (e.g., Blood tests, Urinalysis)", R.drawable.ic_launcher_background),
        MedicalRecord("Prescriptions", "Records medications and dosages prescribed by doctors", R.drawable.ic_launcher_foreground),
        MedicalRecord("Surgical Records", "Documents past surgeries or medical procedures", R.drawable.ic_launcher_foreground)
    )

    Column {
        // Top Bar
        TopAppBarUI()

        Box(
            modifier = Modifier
                .weight(1f) // Mendorong konten agar BottomNavigation menempel di bawah
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
                // Padding for the whole content

            ) {
                // Search Bar
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Handle search query */ },
                    label = { Text("Search Records") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Add New Record Button
                Button(
                    onClick = { /* Handle Add New Record click */ },
                    modifier = Modifier
                        .fillMaxWidth() // Makes the button fill the width available
                        .padding(vertical = 8.dp) // Adds vertical spacing between the button and list
                ) {
                    Text(text = "Add New Record")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Heading for records section
                Text(
                    text = "Medical Record", // Updated the text to match your design
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Medical Records List
                medicalRecords.forEach { record ->
                    RecordItem(record = record)
                }
            }
        }

        BottomNavigationBarUI(navController)

    }
}

@Composable
fun RecordItem(record: MedicalRecord) {
    // Record item layout with rounded corners
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        // Text details for record
        Column {
            Text(
                text = record.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = record.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecordScreen() {
    MyApplicationTheme {
        RecordUI(navController = rememberNavController())
    }
}

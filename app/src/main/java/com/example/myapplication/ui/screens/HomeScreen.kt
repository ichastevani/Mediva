package com.example.myapplication.ui.screens

import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.PMRScreenEnum
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModels.MetaMaskViewModel
import com.example.myapplication.ui.viewModels.PMRLoginUiState
import com.example.myapplication.ui.viewModels.PMRViewModel

@Composable
fun HomeScreen(
  navController: NavHostController,
  metaMaskViewModel: MetaMaskViewModel,
  pmrViewModel: PMRViewModel,
  snackbarHost: SnackbarHostState,
) {
  var authToken by remember { mutableStateOf("") }

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
        authToken = (uiStatePMR.login as PMRLoginUiState.Success).token
      }

      else -> {
        navController.navigate(PMRScreenEnum.Welcome.name)
      }

    }
  }

  HomeUI(
    navController
  )
}

@Composable
fun HomeUI(
  navController: NavHostController,
) {
  Column {
    // Top Navigation
    TopAppBarUI()
    // Konten halaman utama dengan weight(1f) agar mendorong bottom navigation ke bawah
    Box(
      modifier = Modifier
        .weight(1f) // Mendorong konten agar BottomNavigation menempel di bawah
        .fillMaxWidth(),
      contentAlignment = Alignment.Center
    ) {
      Text(text = "Ini Halaman Home")
    }
    // Bottom Navigation
    BottomNavigationBarUI(navController)
  }
}

@Composable
fun LoadingHomeScreen(modifier: Modifier = Modifier) {
  Image(
    modifier = modifier.size(200.dp),
    painter = painterResource(R.drawable.loading_img),
    contentDescription = stringResource(R.string.loading)
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUI(){
  var expanded by remember { mutableStateOf(false) }

  TopAppBar(
    title = { Text(stringResource(R.string.app_name)) },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = Color(parseColor("#EEF3FC")), // Warna Hex
      titleContentColor = Color(parseColor("#1F323F"))
    ),
    actions = {
      IconButton(onClick = { expanded = true }) {
        Icon(
          imageVector = Icons.Default.MoreVert,
          contentDescription = "Account",
        )
      }
      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        DropdownMenuItem(
          text = { Text("Profile") },
          onClick = {
            expanded = false
            // Arahkan ke halaman Profile
          }
        )
        DropdownMenuItem(
          text = { Text("Logout") },
          onClick = {
            expanded = false
            // Tambahkan logika logout
          }
        )
      }
    }
  )
}

sealed class MenuBottomNavigationBar(
  val route: String,
  val title: String,
  val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
  data object Home : MenuBottomNavigationBar(PMRScreenEnum.Home.name, "Home", Icons.Default.Home)
  data object Record : MenuBottomNavigationBar(PMRScreenEnum.Record.name, "Record", Icons.Default.Add)
  data object Permission : MenuBottomNavigationBar(PMRScreenEnum.Permission.name, "Permission", Icons.Filled.Settings) // Use Settings icon
  data object Activity : MenuBottomNavigationBar(PMRScreenEnum.Activity.name, "Activity Log", Icons.Filled.Assignment) // Use Assignment icon
  data object Profile : MenuBottomNavigationBar(PMRScreenEnum.Profile.name, "Profile", Icons.Default.Groups)
}

@Composable
fun BottomNavigationBarUI(navController: NavController) {
  val items = listOf(
    MenuBottomNavigationBar.Home,
    MenuBottomNavigationBar.Record,
    MenuBottomNavigationBar.Permission,
    MenuBottomNavigationBar.Activity,
    MenuBottomNavigationBar.Profile
  )
//  var selectedItem by remember { mutableIntStateOf(0) }

  val currentRoute = navController.currentDestination?.route

  NavigationBar(
    containerColor = Color(0xFFEEF3FC), // Warna latar belakang
    contentColor = Color(0xFF1F323F)
  ) {
    items.forEachIndexed { index, screen ->
      NavigationBarItem(
        selected = currentRoute == screen.route,
        onClick = {
          navController.navigate(screen.route)
        },
        icon = { Icon(
          screen.icon,
          contentDescription = screen.title,
          modifier = Modifier.size(28.dp)
        )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = Color.White,
          indicatorColor = Color(0xFF1F323F),
          unselectedIconColor = Color(0xFF1F323F),
        )
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeUIScreen() {
  MyApplicationTheme {
    HomeUI(
      navController = rememberNavController()
    )
  }
}
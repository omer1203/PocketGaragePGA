package com.example.homepage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.example.registrationcheck.RegistrationScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.homepage.ui.theme.HomePageTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePageTheme { // Apply your app's theme
                AppNavigation() // Call your navigation setup
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registration" // Start at the registration screen
    ) {
        composable("registration") {
            RegistrationScreen(
                onRegistrationComplete = {
                    navController.navigate("home") {
                        popUpTo("registration") { inclusive = true } // Clears registration from the backstack
                    }
                }
            )
        }

        composable("home") {
            Screen() // Ensure this is reachable
        }
    }
}


@Composable
fun Screen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(navController = navController)
            }
        }
    ) {
        Scaffold(
            topBar = { TopBar(onOpenDrawer = { scope.launch { drawerState.open() } }) }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    ScreenContent(modifier = Modifier.padding(padding))
                }
                composable(
                    route = "marketAnalyzer/{vin}",
                    arguments = listOf(navArgument("vin") { type = NavType.StringType })
                ) { backStackEntry ->
                    val vin = backStackEntry.arguments?.getString("vin") ?: ""
                    MarketAnalyzerScreen(vin) // Pass the VIN to the screen
                }
                composable("AnnualMaintenance") {
                    AnnualMaintenanceScreen()
                }
            }
        }
    }

}

@Composable
fun DrawerContent(navController: NavController, modifier: Modifier = Modifier) {
    Text(
        text = "Pocket Garage",
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
    )
    BoxDivider()
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                modifier = Modifier.size(27.dp)
            )
        },
        label = {
            Text(
                text = "Home",
                fontSize = 17.sp,
            )
        },
        selected = false,
        onClick = {
            navController.navigate("home") // Navigate to Home Screen
        }
    )
    Spacer(modifier = Modifier.height(4.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.Build,
                contentDescription = "Recommended Shops",
                modifier = Modifier.size(27.dp)
            )
        },
        label = {
            Text(
                text = "Recommended Shops",
                fontSize = 17.sp,
            )
        },
        selected = false,
        onClick = {
            // add reccomended shop navigation here
        }
    )
    Spacer(modifier = Modifier.height(4.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Market Value",
                modifier = Modifier.size(27.dp)
            )
        },
        label = {
            Text(
                text = "Market Value",
                fontSize = 17.sp,
            )
        },
        selected = false,
        onClick = {
            navController.navigate("marketAnalyzer/19UUB1F32KA010174") // Example VIN
        }


    )
    Spacer(modifier = Modifier.height(4.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Annual Maintenance",
                modifier = Modifier.size(27.dp)
            )
        },
        label = {
            Text(
                text = "Annual Maintenance",
                fontSize = 17.sp,
            )
        },
        selected = false,
        onClick = {
            navController.navigate("AnnualMaintenance")
        }
    )
    Spacer(modifier = Modifier.height(4.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Invoice",
                modifier = Modifier.size(27.dp)
            )
        },
        label = {
            Text(
                text = "Invoice",
                fontSize = 17.sp,
            )
        },
        selected = false,
        onClick = {
            navController.navigate("marketAnalyzer")
        }
    )
}



@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    // Sample list of cars
    val cars = listOf("Toyota Camry", "Honda Civic", "Ford F-150", "Tesla Model 3")
    var selectedCar by remember { mutableStateOf(cars.firstOrNull() ?: "") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // Sample maintenance data (based on the selected car)
    val maintenanceData = when (selectedCar) {
        "Toyota Camry" -> listOf("Oil Change - Due in 10 days", "Tire Rotation - Due in 15 days")
        "Honda Civic" -> listOf("Brake Inspection - Due in 5 days", "Battery Check - Due in 20 days")
        "Ford F-150" -> listOf("Oil Change - Due in 7 days", "Transmission Check - Due in 30 days")
        "Tesla Model 3" -> listOf("Software Update - Due in 2 days", "Battery Inspection - Due in 50 days")
        else -> emptyList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dropdown for car selection
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Select a Car: $selectedCar",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { isDropdownExpanded = true }
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.6f))
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                cars.forEach { car ->
                    DropdownMenuItem(
                        text = { Text(text = car) },
                        onClick = {
                            selectedCar = car
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Maintenance List for the Selected Car
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(maintenanceData) { maintenance ->
                MaintenanceCard(maintenance)
            }
        }
    }
}

@Composable
fun MaintenanceCard(maintenance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = maintenance,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun BoxDivider(
    color: Color = Color.Gray,
    thickness: Float = 1f
) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Take full width
            .height(thickness.dp) // Define the thickness
            .background(color) // Apply the color
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenDrawer: () -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) } // State for dropdown

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.6f)
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(28.dp)
                    .clickable { onOpenDrawer() }
            )
        },
        title = {
            Text(text = "Menu")
        },
        actions = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account",
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(28.dp)
                    .clickable { isDropdownExpanded = true }
            )
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Profile") },
                    onClick = { isDropdownExpanded = false /* Handle Profile */ }
                )
                DropdownMenuItem(
                    text = { Text("My Vehicles") },
                    onClick = { isDropdownExpanded = false /* Handle My Vehicles */ }
                )
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = { isDropdownExpanded = false /* Handle Logout */ }
                )
            }
        }
    )
}

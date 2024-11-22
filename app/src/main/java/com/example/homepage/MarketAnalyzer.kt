package com.example.homepage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MarketAnalyzerScreen(vin: String) {
    val apiKey = "cedi9kx24_9jaahubqg_olz1ldnji" // Replace with your CarsXE App Key
    val scope = rememberCoroutineScope()
    var marketValue by remember { mutableStateOf<MarketValueResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(vin) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getMarketValue(apiKey, vin)
                marketValue = response
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Market Value Analyzer",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (marketValue != null) {
            Text(text = "Market Value for VIN: $vin", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Minimum Value: \$${marketValue?.minValue}")
            Text(text = "Maximum Value: \$${marketValue?.maxValue}")
            Text(text = "Average Value: \$${marketValue?.averageValue}")
        } else if (errorMessage != null) {
            Text(text = "Error: $errorMessage", color = Color.Red)
        } else {
            Text(text = "Fetching market value...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

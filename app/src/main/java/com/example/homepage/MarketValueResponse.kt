package com.example.homepage

data class MarketValueResponse(
    val success: Boolean,      // Whether the API call was successful
    val minValue: Double,      // Minimum market value of the car
    val maxValue: Double,      // Maximum market value of the car
    val averageValue: Double   // Average market value of the car
)

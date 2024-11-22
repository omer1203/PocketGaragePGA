package com.example.homepage

import retrofit2.http.GET
import retrofit2.http.Query

// Define the CarsXE API interface
interface CarsXeApiService {

    // Endpoint for retrieving market value of a car
    @GET("market_value")
    suspend fun getMarketValue(
        @Query("key") apiKey: String,  // The API key for authentication
        @Query("vin") vin: String     // The VIN (Vehicle Identification Number) for the car
    ): MarketValueResponse
}

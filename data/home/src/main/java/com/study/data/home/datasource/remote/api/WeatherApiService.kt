//package com.osprey.data.home.datasource.remote.api
//
//import com.osprey.data.home.datasource.remote.model.response.AirQualityResponse
//import com.osprey.data.home.datasource.remote.model.response.WeatherResponse
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface WeatherApiService {
//    @GET("data/2.5/weather")
//    suspend fun getCurrentWeather(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//        @Query("appid") apiKey: String,
//        @Query("units") units: String = "metric"
//    ): WeatherResponse
//
//    @GET("data/2.5/air_pollution")
//    suspend fun getAirQuality(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//        @Query("appid") apiKey: String
//    ): AirQualityResponse
//}
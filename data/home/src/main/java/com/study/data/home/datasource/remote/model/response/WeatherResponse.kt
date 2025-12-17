//package com.osprey.data.home.datasource.remote.model.response
//
//import com.google.gson.annotations.SerializedName
//
//data class WeatherResponse(
//    @SerializedName("main")
//    val main: Main,
//    @SerializedName("weather")
//    val weather: List<Weather>,
//    @SerializedName("coord")
//    val coord: Coord
//)
//
//data class Main(
//    @SerializedName("temp")
//    val temp: Double,
//    @SerializedName("humidity")
//    val humidity: Int,
//    @SerializedName("pressure")
//    val pressure: Int
//)
//
//data class Weather(
//    @SerializedName("main")
//    val main: String,
//    @SerializedName("description")
//    val description: String,
//    @SerializedName("icon")
//    val icon: String
//)
//
//data class Coord(
//    @SerializedName("lat")
//    val lat: Double,
//    @SerializedName("lon")
//    val lon: Double
//)
//
//data class AirQualityResponse(
//    @SerializedName("list")
//    val list: List<AirQuality>
//)
//
//data class AirQuality(
//    @SerializedName("components")
//    val components: Components
//)
//
//data class Components(
//    @SerializedName("pm2_5")
//    val pm2_5: Double
//)
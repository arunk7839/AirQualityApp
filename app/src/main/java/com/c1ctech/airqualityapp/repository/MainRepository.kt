package com.c1ctech.airqualityapp.repository

import com.c1ctech.airqualityapp.network.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAirQualityData(lat: Float, lon: Float, areaclass: String) =
        retrofitService.getAirQualityData(lat, lon, areaclass)

    fun getAirQualityData(lat: Float, lon: Float) =
        retrofitService.getAirQualityData(lat, lon)
}
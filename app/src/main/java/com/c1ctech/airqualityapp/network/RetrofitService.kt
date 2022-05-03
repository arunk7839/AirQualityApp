package com.c1ctech.airqualityapp.network

import com.c1ctech.airqualityapp.AirQualityData.AirQuality
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("0.1/")
    fun getAirQualityData(
        @Query("lat") lat: Float?,
        @Query("lon") lon: Float?,
        @Query("areaclass") areaclass: String?
    ): Call<AirQuality>

    @GET("0.1/")
    fun getAirQualityData(
        @Query("lat") lat: Float?,
        @Query("lon") lon: Float?): Call<AirQuality>


    companion object {

        var retrofitService: RetrofitService? = null

        //Create the RetrofitService instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.met.no/weatherapi/airqualityforecast/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
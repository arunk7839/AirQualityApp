package com.c1ctech.airqualityapp.AirQualityData

data class Time(
    val from: String,
    val reason: Reason,
    val to: String,
    val variables: Variables
)
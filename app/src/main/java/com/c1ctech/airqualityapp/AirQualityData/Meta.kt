package com.c1ctech.airqualityapp.AirQualityData

data class Meta(
    val location: Location,
    val reftime: String,
    val sublocations: List<Any>,
    val superlocation: Superlocation
)
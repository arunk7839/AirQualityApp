package com.c1ctech.airqualityapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c1ctech.airqualityapp.AirQualityData.AirQuality
import com.c1ctech.airqualityapp.AirQualityData.Time
import com.c1ctech.airqualityapp.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {


    val time = MutableLiveData<List<Time>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllData(lat: Float, lon: Float, areaclass: String?) {

        val response: Any

        if (areaclass == null) {
            response = repository.getAirQualityData(lat, lon)
        } else {
            response = repository.getAirQualityData(lat, lon, areaclass)
        }

        response.enqueue(object : Callback<AirQuality> {
            override fun onResponse(call: Call<AirQuality>, response: Response<AirQuality>) {
                time.postValue(response.body()?.data?.time)
            }

            override fun onFailure(call: Call<AirQuality>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}
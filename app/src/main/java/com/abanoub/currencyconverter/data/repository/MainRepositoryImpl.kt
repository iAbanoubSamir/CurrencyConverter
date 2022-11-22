package com.abanoub.currencyconverter.data.repository

import com.abanoub.currencyconverter.data.api.RatesApiService
import com.abanoub.currencyconverter.data.model.RateResponse
import com.abanoub.currencyconverter.utils.Constants.API_KEY
import com.abanoub.currencyconverter.utils.Resource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: RatesApiService
) : MainRepository {

    override suspend fun getRates(base: String): Resource<RateResponse> {
        return try {
            val response = apiService.getRates(API_KEY, base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error when loading data.")
        }
    }
}
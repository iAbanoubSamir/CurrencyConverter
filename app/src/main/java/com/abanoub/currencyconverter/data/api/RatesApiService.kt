package com.abanoub.currencyconverter.data.api

import com.abanoub.currencyconverter.data.model.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApiService {

    @GET("latest")
    suspend fun getRates(
        @Query("apikey") apiKey: String,
        @Query("base") base: String
    ): Response<RateResponse>

}
package com.abanoub.currencyconverter.data.repository

import com.abanoub.currencyconverter.data.model.RateResponse
import com.abanoub.currencyconverter.utils.Resource

interface MainRepository {

    suspend fun getRates(base: String): Resource<RateResponse>

}
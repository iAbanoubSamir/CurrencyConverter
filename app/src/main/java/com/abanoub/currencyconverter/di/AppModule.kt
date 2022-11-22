package com.abanoub.currencyconverter.di

import com.abanoub.currencyconverter.data.api.RatesApiService
import com.abanoub.currencyconverter.data.repository.MainRepository
import com.abanoub.currencyconverter.data.repository.MainRepositoryImpl
import com.abanoub.currencyconverter.utils.Constants.BASE_URL
import com.abanoub.currencyconverter.utils.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRatesApi(): RatesApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RatesApiService::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(apiService: RatesApiService): MainRepository =
        MainRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideDispatches(): DispatchersProvider = object : DispatchersProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}
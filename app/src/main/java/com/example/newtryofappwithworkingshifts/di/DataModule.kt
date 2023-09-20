package com.example.newtryofappwithworkingshifts.di


import com.example.newtryofappwithworkingshifts.data.db.SheduleDatabase
import com.example.newtryofappwithworkingshifts.data.db.SheduleStorage
import com.example.newtryofappwithworkingshifts.data.db.impl.SheduleStorageImpl
import com.example.newtryofappwithworkingshifts.data.repository.SheduleRepositoryImpl
import com.example.newtryofappwithworkingshifts.domain.repositories.SheduleRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<SheduleStorage> { SheduleStorageImpl(sheduleDatabase = get()) }


    single<SheduleRepository> { SheduleRepositoryImpl(sheduleStorage = get()) }

    single<SheduleDatabase> { SheduleDatabase.getInstance(context = get()) }
}
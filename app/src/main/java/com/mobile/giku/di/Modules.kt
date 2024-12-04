package com.mobile.giku.di

import com.mobile.giku.BuildConfig
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.model.remote.auth.AuthApiService
import com.mobile.giku.repository.auth.AuthRepository
import com.mobile.giku.viewmodel.auth.ForgotPasswordViewModel
import com.mobile.giku.viewmodel.auth.LoginViewModel
import com.mobile.giku.viewmodel.auth.RegisterViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {
    single { AuthDataStore(get()) }
    single { AuthRepository(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
}

val networkModules = module {
    single {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()

    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(AuthApiService::class.java)
    }
}
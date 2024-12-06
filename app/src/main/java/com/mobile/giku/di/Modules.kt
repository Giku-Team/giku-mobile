package com.mobile.giku.di

import com.mobile.giku.BuildConfig
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.model.remote.auth.AuthApiService
import com.mobile.giku.repository.auth.AuthRepository
import com.mobile.giku.utils.AuthErrorMapper
import com.mobile.giku.utils.StringProvider
import com.mobile.giku.utils.StringProviderImpl
import com.mobile.giku.viewmodel.auth.ForgotPasswordViewModel
import com.mobile.giku.viewmodel.auth.LoginViewModel
import com.mobile.giku.viewmodel.auth.RegisterViewModel
import com.mobile.giku.viewmodel.auth.SetNewPasswordViewModel
import com.mobile.giku.viewmodel.auth.SharedAuthViewModel
import com.mobile.giku.viewmodel.auth.VerificationCodeViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {
    single<StringProvider> { StringProviderImpl(get()) }

    single { AuthErrorMapper.LoginErrorMapper(get()) }
    single { AuthErrorMapper.RegisterErrorMapper(get()) }
    single { AuthErrorMapper.ForgotPasswordErrorMapper(get()) }
    single { AuthErrorMapper.VerificationCodeErrorMapper(get()) }
    single { AuthErrorMapper.SetNewPasswordErrorMapper(get()) }

    single { AuthDataStore(get()) }
    single { AuthRepository(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { VerificationCodeViewModel(get()) }
    viewModel { SetNewPasswordViewModel(get()) }
    viewModel { SharedAuthViewModel() }
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
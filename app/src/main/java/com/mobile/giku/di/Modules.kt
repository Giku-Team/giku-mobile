package com.mobile.giku.di

import com.google.gson.GsonBuilder
import com.mobile.giku.BuildConfig
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.model.remote.auth.AuthApiService
import com.mobile.giku.model.remote.child.ChildApiService
import com.mobile.giku.model.remote.nutrient.NutrientApiService
import com.mobile.giku.repository.auth.AuthRepository
import com.mobile.giku.repository.child.ChildRepository
import com.mobile.giku.repository.nutrient.NutrientRepository
import com.mobile.giku.utils.AuthErrorMapper
import com.mobile.giku.utils.DecimalTypeAdapter
import com.mobile.giku.utils.StringProvider
import com.mobile.giku.utils.StringProviderImpl
import com.mobile.giku.viewmodel.analysis.AnalysisViewModel
import com.mobile.giku.viewmodel.auth.ForgotPasswordViewModel
import com.mobile.giku.viewmodel.auth.LoginViewModel
import com.mobile.giku.viewmodel.auth.RegisterViewModel
import com.mobile.giku.viewmodel.auth.SetNewPasswordViewModel
import com.mobile.giku.viewmodel.auth.SharedAuthViewModel
import com.mobile.giku.viewmodel.auth.VerificationCodeViewModel
import com.mobile.giku.viewmodel.child.ChildProfileViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
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
    single { AuthRepository(get(), get(), get(), get(), get(), get())}
    single { NutrientRepository(get(), get()) }
    single { ChildRepository(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { VerificationCodeViewModel(get()) }
    viewModel { SetNewPasswordViewModel(get()) }
    viewModel { SharedAuthViewModel() }
    viewModel { AnalysisViewModel(get()) }
    viewModel { ChildProfileViewModel(get()) }
}

val networkModules = module {
    single(qualifier = named("baseOkHttpClient")) {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()
    }

    single(qualifier = named("nutritionOkHttpClient")) {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        val gson = GsonBuilder()
            .registerTypeAdapter(Double::class.java, DecimalTypeAdapter())
            .create()
        gson
    }

    single(qualifier = named("baseRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get<OkHttpClient>(named("baseOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single(qualifier = named("baseNutritionRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.NUTRITION_BASE_URL)
            .client(get<OkHttpClient>(named("nutritionOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>(named("baseRetrofit")).create(AuthApiService::class.java)
    }

    single {
        get<Retrofit>(named("baseNutritionRetrofit")).create(NutrientApiService::class.java)
    }

    single {
        get<Retrofit>(named("baseRetrofit")).create(ChildApiService::class.java)
    }
}
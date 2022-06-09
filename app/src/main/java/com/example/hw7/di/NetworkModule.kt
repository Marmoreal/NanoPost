package com.example.hw7.di

import android.content.ContentResolver
import android.content.Context
import com.example.hw7.data.repository.PreferencesRepositoryImpl
import com.example.hw7.domain.AuthApiService
import com.example.hw7.domain.NanoPostApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class AuthRetrofit

@Qualifier
annotation class ApiRetrofit

@Qualifier
annotation class AuthClient

@Qualifier
annotation class ApiClient

@Qualifier
annotation class AuthInterceptor

@Qualifier
annotation class ExceptionInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    @ExceptionInterceptor
    fun provideExceptionInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            when (response.code) {
                400 -> throw IOException("password is incorrect")
            }
            response
        }
    }

    @Provides
    @Singleton
    @AuthInterceptor
    fun provideAuthInterceptor(
        preferencesRepository: PreferencesRepositoryImpl
    ): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            preferencesRepository.getToken()?.let { token ->
                requestBuilder.header(
                    "Authorization",
                    "Bearer $token"
                )
            }

            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    @ApiClient
    fun provideOkHttpClient(
        @AuthInterceptor authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        @ExceptionInterceptor exceptionInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(exceptionInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    @ApiRetrofit
    fun provideNanoPostMRetrofit(
        @ApiClient client: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://nanopost.evolitist.com/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    @AuthClient
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ExceptionInterceptor exceptionInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(exceptionInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthMRetrofit(
        @ApiClient client: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://nanopost.evolitist.com/api/auth/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(@AuthRetrofit retrofit: Retrofit): AuthApiService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideNanoPostApiService(@ApiRetrofit retrofit: Retrofit): NanoPostApiService {
        return retrofit.create()
    }
}
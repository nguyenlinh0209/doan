package com.osprey.data.user.di

import android.app.Application
import com.google.gson.Gson
import com.osprey.config.model.Config
import com.osprey.data.user.clienthtpp.AuthInterceptor
import com.osprey.data.user.clienthtpp.OkHttpProvider
import com.osprey.data.user.datasource.remote.interceptor.AuthDataSource
import com.osprey.data.user.datasource.remote.interceptor.AuthDataSourceImpl
import com.osprey.data.user.remote.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        app: Application,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpProvider.provideOkHttpClient(app,authInterceptor)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.API.BASE_AUTH)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthDataSource(
        api: AuthService,
        gson: Gson
    ): AuthDataSource {
        return AuthDataSourceImpl(api, gson)
    }
}

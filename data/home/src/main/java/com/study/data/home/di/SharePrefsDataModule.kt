package com.osprey.data.home.di

import android.content.Context
import com.google.gson.Gson
import com.osprey.data.common.datasource.AppSharePrefs
import com.osprey.data.common.datasource.AppSharePrefsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharePrefsDataModule {
    @Provides
    @Singleton
    fun provideAppSharedPrefs(
        @ApplicationContext context: Context,
        gson: Gson
    ): AppSharePrefs {
        val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return AppSharePrefsImpl(sharedPrefs, gson)
    }

}
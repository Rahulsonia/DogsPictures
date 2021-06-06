package com.rahulsonia.dogspictures.di

import com.rahulsonia.dogspictures.api.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(DogApi.BASE_URL).build()

    @Provides
    @Singleton
    fun provideDogApi(retrofit: Retrofit): DogApi = retrofit.create(DogApi::class.java)
}
package com.alexisdev.network.di

import com.alexisdev.network.api.FILM_API_BASE_URL
import com.alexisdev.network.api.FilmApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { provideRetrofit() }
    single { provideFilmApiService(get()) }
    single { provideHttpInterceptor() }
    single { provideOkHttpClient(get()) }

}


private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(FILM_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideFilmApiService(retrofit: Retrofit): FilmApiService {
    return retrofit.create(FilmApiService::class.java)
}


private fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun provideHttpInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
package com.alexisdev.network.api

import com.alexisdev.network.model.FilmsResponseDto
import retrofit2.http.GET

const val FILM_API_BASE_URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/"

interface FilmApiService {

    @GET("films.json")
    suspend fun getAllFilms(): FilmsResponseDto

}
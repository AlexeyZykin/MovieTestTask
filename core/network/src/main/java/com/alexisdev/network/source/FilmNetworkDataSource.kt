package com.alexisdev.network.source

import com.alexisdev.common.Response
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.flow.Flow

interface FilmNetworkDataSource {
    fun getAllFilms(): Flow<Response<List<FilmDto>>>
    fun getFilmDetails(filmId: Int): Flow<FilmDto>
    fun getAllGenres(): Flow<List<GenreDto>>
    fun getFilmsByGenre(genre: GenreDto?): Flow<List<FilmDto>>
}

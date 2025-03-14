package com.alexisdev.domain.repo

import com.alexisdev.common.Response
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface FilmRepo {
    fun getAllFilms(): Flow<Response<List<Film>>>
    fun getFilmDetails(filmId: Int): Flow<Film>
    fun getAllGenres(): Flow<List<Genre>>
    fun getFilmsByGenre(genre: Genre?): Flow<List<Film>>
}
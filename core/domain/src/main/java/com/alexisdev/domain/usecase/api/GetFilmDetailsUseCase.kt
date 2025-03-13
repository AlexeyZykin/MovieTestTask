package com.alexisdev.domain.usecase.api

import com.alexisdev.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface GetFilmDetailsUseCase {

    fun execute(filmId: Int): Flow<Film>
}
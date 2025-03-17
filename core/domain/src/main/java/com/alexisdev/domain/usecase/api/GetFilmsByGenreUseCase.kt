package com.alexisdev.domain.usecase.api

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GetFilmsByGenreUseCase {

    fun execute(genre: Genre): Flow<List<Film>>
}
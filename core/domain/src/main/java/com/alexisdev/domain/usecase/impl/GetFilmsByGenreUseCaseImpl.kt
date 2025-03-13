package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.usecase.api.GetFilmsByGenreUseCase
import kotlinx.coroutines.flow.Flow

class GetFilmsByGenreUseCaseImpl : GetFilmsByGenreUseCase {
    override fun execute(genre: Genre): Flow<List<Film>> {
        TODO("Not yet implemented")
    }
}
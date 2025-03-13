package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import kotlinx.coroutines.flow.Flow

class GetFilmDetailsUseCaseImpl : GetFilmDetailsUseCase {
    override fun execute(filmId: Int): Flow<Film> {
        TODO("Not yet implemented")
    }
}
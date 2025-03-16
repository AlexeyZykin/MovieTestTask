package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.repo.FilmRepo
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import kotlinx.coroutines.flow.Flow

internal class GetFilmDetailsUseCaseImpl(private val filmRepo: FilmRepo) : GetFilmDetailsUseCase {
    override fun execute(filmId: Int): Flow<Film> {
        return filmRepo.getFilmDetails(filmId)
    }
}
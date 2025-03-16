package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repo.FilmRepo
import com.alexisdev.domain.usecase.api.GetFilmsByGenreUseCase
import kotlinx.coroutines.flow.Flow

internal class GetFilmsByGenreUseCaseImpl(private val filmRepo: FilmRepo) : GetFilmsByGenreUseCase {
    override fun execute(genre: Genre?): Flow<List<Film>> {
        return filmRepo.getFilmsByGenre(genre)
    }
}
package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repo.FilmRepo
import com.alexisdev.domain.usecase.api.GetSelectedGenreUseCase
import kotlinx.coroutines.flow.Flow

internal class GetSelectedGenreUseCaseImpl(private val filmRepo: FilmRepo) : GetSelectedGenreUseCase {
    override fun execute(): Flow<Genre?> {
        return filmRepo.getSelectedGenre()
    }
}
package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repo.FilmRepo
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import kotlinx.coroutines.flow.Flow

internal class GetAllGenresUseCaseImpl(private val filmRepo: FilmRepo) : GetAllGenresUseCase {
    override fun execute(): Flow<List<Genre>> {
        return filmRepo.getAllGenres()
    }
}
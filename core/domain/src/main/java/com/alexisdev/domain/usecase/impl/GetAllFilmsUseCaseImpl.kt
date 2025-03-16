package com.alexisdev.domain.usecase.impl

import com.alexisdev.common.Response
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.repo.FilmRepo
import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import kotlinx.coroutines.flow.Flow

internal class GetAllFilmsUseCaseImpl(private val filmRepo: FilmRepo) : GetAllFilmsUseCase {
    override fun execute(): Flow<Response<List<Film>>> {
        return filmRepo.getAllFilms()
    }
}
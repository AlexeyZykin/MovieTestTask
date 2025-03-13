package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Film
import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import kotlinx.coroutines.flow.Flow

class GetAllFilmsUseCaseImpl : GetAllFilmsUseCase {
    override fun execute(): Flow<List<Film>> {
        TODO("Not yet implemented")
    }
}
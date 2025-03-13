package com.alexisdev.domain.usecase.impl

import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import kotlinx.coroutines.flow.Flow

class GetAllGenresUseCaseImpl : GetAllGenresUseCase {
    override fun execute(): Flow<List<Genre>> {
        TODO("Not yet implemented")
    }
}
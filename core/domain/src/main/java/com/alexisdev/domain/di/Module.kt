package com.alexisdev.domain.di

import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import com.alexisdev.domain.usecase.api.GetFilmsByGenreUseCase
import com.alexisdev.domain.usecase.impl.GetAllFilmsUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetAllGenresUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetFilmDetailsUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetFilmsByGenreUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllFilmsUseCase> { GetAllFilmsUseCaseImpl() }
    factory<GetAllGenresUseCase> { GetAllGenresUseCaseImpl() }
    factory<GetFilmDetailsUseCase> { GetFilmDetailsUseCaseImpl() }
    factory<GetFilmsByGenreUseCase> { GetFilmsByGenreUseCaseImpl() }
}

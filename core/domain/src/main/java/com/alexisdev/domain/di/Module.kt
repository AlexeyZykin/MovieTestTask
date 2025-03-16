package com.alexisdev.domain.di

import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import com.alexisdev.domain.usecase.api.GetFilmsByGenreUseCase
import com.alexisdev.domain.usecase.api.GetSelectedGenreUseCase
import com.alexisdev.domain.usecase.impl.GetAllFilmsUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetAllGenresUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetFilmDetailsUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetFilmsByGenreUseCaseImpl
import com.alexisdev.domain.usecase.impl.GetSelectedGenreUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllFilmsUseCase> { GetAllFilmsUseCaseImpl(get()) }
    factory<GetAllGenresUseCase> { GetAllGenresUseCaseImpl(get()) }
    factory<GetFilmDetailsUseCase> { GetFilmDetailsUseCaseImpl(get()) }
    factory<GetFilmsByGenreUseCase> { GetFilmsByGenreUseCaseImpl(get()) }
    factory<GetSelectedGenreUseCase> { GetSelectedGenreUseCaseImpl(get()) }
}

package com.alexisdev.data.di

import com.alexisdev.data.repo.FilmRepoImpl
import com.alexisdev.domain.repo.FilmRepo
import org.koin.dsl.module

val dataModule = module {
    single<FilmRepo> { FilmRepoImpl(get()) }
}
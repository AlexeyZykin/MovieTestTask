package com.alexisdev.film_catalog.di

import com.alexisdev.film_catalog.FilmCatalogViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val filmCatalogFeatureModule = module {
    viewModelOf(::FilmCatalogViewModel)
}
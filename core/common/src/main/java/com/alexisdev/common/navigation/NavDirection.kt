package com.alexisdev.common.navigation

sealed interface NavDirection {

    data class FilmCatalogToFilmDetails(val filmId: Int) : NavDirection

}
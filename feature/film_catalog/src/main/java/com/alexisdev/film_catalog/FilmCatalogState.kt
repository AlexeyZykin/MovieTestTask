package com.alexisdev.film_catalog

import com.alexisdev.film_catalog.model.FilmUi
import com.alexisdev.film_catalog.model.GenreUi

sealed interface FilmCatalogState {
    data class Content(
        val genres: List<GenreUi>,
        val films: List<FilmUi>,
        val selectedGenre: GenreUi? = null
    ) : FilmCatalogState

    data object Loading : FilmCatalogState
    data class Error(val msg: String) : FilmCatalogState
}
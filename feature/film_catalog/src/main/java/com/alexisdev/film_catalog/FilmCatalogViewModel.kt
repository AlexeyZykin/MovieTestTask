package com.alexisdev.film_catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisdev.common.Response
import com.alexisdev.domain.usecase.api.GetAllFilmsUseCase
import com.alexisdev.domain.usecase.api.GetAllGenresUseCase
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import com.alexisdev.domain.usecase.api.GetFilmsByGenreUseCase
import com.alexisdev.domain.usecase.api.GetSelectedGenreUseCase
import com.alexisdev.film_catalog.mapper.toFilmUi
import com.alexisdev.film_catalog.mapper.toGenre
import com.alexisdev.film_catalog.mapper.toGenreUi
import com.alexisdev.film_catalog.model.GenreUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class FilmCatalogViewModel(
    private val getAllFilmsUseCase: GetAllFilmsUseCase,
    private val getAllGenresUseCase: GetAllGenresUseCase,
    private val getFilmsByGenreUseCase: GetFilmsByGenreUseCase,
    private val getSelectedGenreUseCase: GetSelectedGenreUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FilmCatalogState>(FilmCatalogState.Loading)
    val uiState: StateFlow<FilmCatalogState> get() = _uiState


    init {
        getAllFilmCatalogData()
        getSelectedGenreUseCase.execute()
            .onEach { genre ->
                _uiState.update { filmCatalogState ->
                    when (filmCatalogState) {
                        is FilmCatalogState.Content -> filmCatalogState.copy(selectedGenre = genre?.toGenreUi())
                        else -> filmCatalogState
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getAllFilmCatalogData() {
        combine(
            getAllFilmsUseCase.execute(),
            getAllGenresUseCase.execute()
        ) { filmsResponse, genresResponse ->
            _uiState.update {
                when (filmsResponse) {
                    is Response.Error -> FilmCatalogState.Error(msg = filmsResponse.message)

                    is Response.Success -> {
                        FilmCatalogState.Content(
                            genres = genresResponse.map { it.toGenreUi() },
                            films = filmsResponse.data.map { it.toFilmUi() },
                            selectedGenre = null
                        )
                    }

                    else -> FilmCatalogState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: FilmCatalogEvent) {
        when (event) {
            is FilmCatalogEvent.OnSelectGenre -> {
                handleOnSelectGenre(event.genre)
            }

            is FilmCatalogEvent.OnRetry -> {
                handleOnRetry()
            }
        }
    }

    private fun handleOnSelectGenre(genreUi: GenreUi) {
        getFilmsByGenreUseCase.execute(genreUi.toGenre())
            .onEach { films ->
                _uiState.update { filmCatalogState ->
                    when (filmCatalogState) {
                        is FilmCatalogState.Content -> {
                            filmCatalogState.copy(films = films.map { it.toFilmUi() })
                        }
                        else -> filmCatalogState
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    private fun handleOnRetry() {
        _uiState.update {
            FilmCatalogState.Loading
        }
        getAllFilmCatalogData()
    }

}


sealed interface FilmCatalogEvent {

    data class OnSelectGenre(val genre: GenreUi) : FilmCatalogEvent

    data object OnRetry : FilmCatalogEvent
}
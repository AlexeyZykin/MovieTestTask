package com.alexisdev.film_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexisdev.common.Response
import com.alexisdev.domain.usecase.api.GetFilmDetailsUseCase
import com.alexisdev.film_details.mapper.toFilmUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class FilmDetailsViewModel(
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<FilmDetailsState> =
        MutableStateFlow(FilmDetailsState.Loading)
    val uiState: StateFlow<FilmDetailsState> = _uiState

    init {
        getFilmDetails()
    }

    private fun getFilmDetails() {
        val filmId = savedStateHandle.get<Int>(ARG_FILM_ID)
            ?.also {
                getFilmDetailsUseCase.execute(it)
                    .onEach { film ->
                        _uiState.update {
                            FilmDetailsState.Content(film.toFilmUi())
                        }
                    }
                    .launchIn(viewModelScope)
            }
    }


    companion object {
        private const val ARG_FILM_ID = "filmId"
    }
}

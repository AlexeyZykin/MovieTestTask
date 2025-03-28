package com.alexisdev.data.repo

import com.alexisdev.common.Response
import com.alexisdev.data.mapper.toFilm
import com.alexisdev.domain.model.Film
import com.alexisdev.domain.model.Genre
import com.alexisdev.domain.repo.FilmRepo
import com.alexisdev.network.source.FilmNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

internal class FilmRepoImpl(private val filmNetworkDataSource: FilmNetworkDataSource) : FilmRepo {

    private val _cachedFilms = MutableStateFlow<List<Film>>(emptyList());
    private val cachedFilms: StateFlow<List<Film>> get() = _cachedFilms

    private val _lastSelectedGenre = MutableStateFlow<Genre?>(null)
    private val lastSelectedGenre: StateFlow<Genre?> get() = _lastSelectedGenre


    override fun getAllFilms(): Flow<Response<List<Film>>> {
        return filmNetworkDataSource.fetchAllFilms().map { response ->
            when (response) {
                is Response.Success -> {
                    val films = response.data.map { it.toFilm() }
                    _cachedFilms.tryEmit(films)
                    Response.Success(films)
                }

                is Response.Error -> {
                    Response.Error(response.message)
                }
            }
        }
    }

    override fun getFilmDetails(filmId: Int): Flow<Film> {
        return cachedFilms.map { films ->
            films.first { it.id == filmId }
        }
    }

    override fun getAllGenres(): Flow<List<Genre>> {
        return cachedFilms
            .map { films ->
                films.flatMap { it.genres }.distinct()
            }
    }

    override fun getFilmsByGenre(genre: Genre): Flow<List<Film>> {
        _lastSelectedGenre.tryEmit(
            if (genre != _lastSelectedGenre.value) {
                genre
            } else {
                null
            }
        )

        return cachedFilms
            .map { films ->
                if (_lastSelectedGenre.value == null) {
                    films
                } else {
                    films.filter { it.genres.contains(_lastSelectedGenre.value) }
                }
            }
    }

    override fun getSelectedGenre(): Flow<Genre?> {
        return lastSelectedGenre
    }
}
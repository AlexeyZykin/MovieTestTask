package com.alexisdev.network.source

import com.alexisdev.common.Response
import com.alexisdev.common.asResponse
import com.alexisdev.network.api.FilmApiService
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

internal class FilmNetworkDataSourceImpl(private val filmApiService: FilmApiService) :
    FilmNetworkDataSource {

    private val _cachedFilms = MutableStateFlow<List<FilmDto>>(emptyList());
    private val cachedFilms: StateFlow<List<FilmDto>> get() = _cachedFilms

    private val lastSelectedGenre = MutableStateFlow<GenreDto?>(null)


    override fun getAllFilms(): Flow<Response<List<FilmDto>>> {
        return flow {
            try {
                val films = filmApiService.getAllFilms().films
                    .sortedWith(
                        compareBy(String.CASE_INSENSITIVE_ORDER) { it.localizedName }
                    )

                _cachedFilms.tryEmit(films)
                emit(Response.Success(data = films))
            } catch (e: Exception) {
                emit(Response.Error(message = "Отсутствует интернет соединение!"))
            }
        }
    }


    override fun getFilmDetails(filmId: Int): Flow<FilmDto> {
        TODO("Not yet implemented")
    }

    override fun getAllGenres(): Flow<List<GenreDto>> {
        return cachedFilms.flatMapLatest { films ->
            flowOf(
                films
                    .flatMap { it.genres }
                    .distinct()
            )
        }
    }

    override fun getFilmsByGenre(genre: GenreDto?): Flow<List<FilmDto>> {
        lastSelectedGenre.tryEmit(
            if (genre != lastSelectedGenre.value) {
                genre
            } else {
                null
            }
        )

        return if (lastSelectedGenre.value == null) {
            cachedFilms
        } else {
            cachedFilms.flatMapLatest { films ->
                flowOf(films.filter { it.genres.contains(genre) })
            }
        }
    }
}
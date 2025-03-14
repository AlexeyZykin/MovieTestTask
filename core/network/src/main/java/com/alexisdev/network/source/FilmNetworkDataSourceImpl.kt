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


    override fun fetchAllFilms(): Flow<Response<List<FilmDto>>> {
        return flow {
            try {
                val films = filmApiService.getAllFilms().films
                    .sortedWith(
                        compareBy(String.CASE_INSENSITIVE_ORDER) { it.localizedName }
                    )

                emit(Response.Success(data = films))
            } catch (e: Exception) {
                emit(Response.Error(message = "Отсутствует интернет соединение!"))
            }
        }
    }

}
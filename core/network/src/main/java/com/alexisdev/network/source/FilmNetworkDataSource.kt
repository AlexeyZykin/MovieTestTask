package com.alexisdev.network.source

import com.alexisdev.common.Response
import com.alexisdev.network.model.FilmDto
import com.alexisdev.network.model.GenreDto
import kotlinx.coroutines.flow.Flow

interface FilmNetworkDataSource {
    fun fetchAllFilms(): Flow<Response<List<FilmDto>>>
}

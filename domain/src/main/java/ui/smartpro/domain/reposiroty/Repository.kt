package ui.smartpro.domain.reposiroty

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.smartpro.data.entities.Movie

interface Repository {
    fun getMovies(): Flow<PagingData<Movie>>
}
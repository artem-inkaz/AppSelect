package ui.smartpro.appselect.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ui.smartpro.data.entities.Movie
import ui.smartpro.domain.reposiroty.Repository
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    fun fetchMovies(): Flow<PagingData<Movie>> {
        return repository.getMovies().cachedIn(viewModelScope)
    }
}
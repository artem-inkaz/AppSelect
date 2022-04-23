package ui.smartpro.domain.reposiroty

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.smartpro.data.api.Api
import ui.smartpro.data.db.MovieDatabase
import ui.smartpro.data.entities.Movie
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val db: MovieDatabase
    ) :
    Repository {

    private val config = PagingConfig(
        pageSize = 5,
        enablePlaceholders = false,
        prefetchDistance = 3
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = config,
            remoteMediator = MovieRemoteMediator(api, db),
            pagingSourceFactory = {
                db.movieDao().getMovie()
            }).flow
    }
}
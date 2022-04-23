package ui.smartpro.domain.reposiroty

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ui.smartpro.data.api.Api
import ui.smartpro.data.db.MovieDatabase
import ui.smartpro.data.entities.Keys
import ui.smartpro.data.entities.Movie
import ui.smartpro.logging.Logger

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: Api,
    private val database: MovieDatabase
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey =
                        if (remoteKeys?.prevKey!! <= getCount()) null else remoteKeys.prevKey!!
                    if (prevKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    if (nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    nextKey
                }
            }
            val apiResponse = api.getAllReviews()
            val repos = apiResponse.results
            val endOfPaginationReached = repos.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.keysDao().clearAllKey()
                    database.movieDao().clearAllMovie()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (repos.size < state.config.pageSize) null else page + 1
                val mapper = repos.map { Movie.Mapper.map(it) }
                val keys = repos.map {
                    Keys(Movie.Mapper.map(it).id, "all", prevKey, nextKey)
                }
                if (nextKey != null) {
                    if (repos.size >= database.movieDao().getCount() && database.movieDao()
                            .getCount() == 0
                    ) {
                        database.keysDao().saveKeys(Keys(0, "all", prevKey, nextKey))
                        database.movieDao().saveMovie(mapper)
                    }
                }
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            Logger.e(e.toString())
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getCount(): Int {
        return api.getAllReviews().num_results
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): Keys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { key ->
                database.keysDao().remoteKeys("all")
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): Keys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                database.keysDao().remoteKeys("all")
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): Keys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.keysDao().remoteKeys("all")
            }
        }
    }
}
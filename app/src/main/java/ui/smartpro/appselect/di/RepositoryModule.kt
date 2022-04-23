package ui.smartpro.appselect.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ui.smartpro.data.db.MovieDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        MovieDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun providesKeyDao(database: MovieDatabase) = database.keysDao()

    @Provides
    @Singleton
    fun providesMovieDao(database: MovieDatabase) = database.movieDao()

}

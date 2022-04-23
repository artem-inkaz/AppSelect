package ui.smartpro.appselect.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.smartpro.domain.reposiroty.Repository
import ui.smartpro.domain.reposiroty.RepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {

    @Binds
    abstract fun bindsDataRepository(repositoryImpl: RepositoryImpl): Repository

}

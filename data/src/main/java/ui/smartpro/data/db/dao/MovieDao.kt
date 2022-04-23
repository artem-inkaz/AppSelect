package ui.smartpro.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ui.smartpro.data.entities.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getMovie(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clearAllMovie()

    @Query("SELECT COUNT(id) FROM movie")
    fun getCount(): Int
}
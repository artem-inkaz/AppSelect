package ui.smartpro.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ui.smartpro.data.db.dao.KeysDao
import ui.smartpro.data.db.dao.MovieDao
import ui.smartpro.data.entities.Keys
import ui.smartpro.data.entities.Movie

@Database(
    entities = [Movie::class, Keys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun keysDao(): KeysDao
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        //Build a local database to store data
        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, MovieDatabase::class.java, "ny_movies_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
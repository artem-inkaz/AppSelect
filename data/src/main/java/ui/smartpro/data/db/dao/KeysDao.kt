package ui.smartpro.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ui.smartpro.data.entities.Keys

@Dao
interface KeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveKeys(key: Keys)

    @Query("SELECT * FROM keys  ORDER BY id DESC")
    suspend fun getKeys(): List<Keys>

    @Query("SELECT * FROM keys WHERE type = :type")
    suspend fun remoteKeys(type: String): Keys

    @Query("DELETE FROM keys")
    suspend fun clearAllKey()

    @Query("SELECT COUNT(id) FROM keys")
    fun getCount(): Int
}
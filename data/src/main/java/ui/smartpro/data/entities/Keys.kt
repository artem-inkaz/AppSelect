package ui.smartpro.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class Keys(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var type: String,
    var prevKey: Int?,
    var nextKey: Int?

)
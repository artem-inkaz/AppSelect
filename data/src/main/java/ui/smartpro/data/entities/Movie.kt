package ui.smartpro.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ui.smartpro.data.model.response.Result

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("display_title")
    val display_title: String,
    @SerializedName("src")
    val src: String,
    @SerializedName("summary_short")
    val summary_short: String
) {
    object Mapper {
        fun map(result: Result?) =
            Movie(
                0,
                result!!.display_title,
                result.multimedia.src,
                result.summary_short,

                )
    }
}

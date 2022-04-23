package ui.smartpro.data.model.response

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("byline")
    val byline: String,
    @SerializedName("critics_pick")
    val critics_pick: Int,
    @SerializedName("date_updated")
    val date_updated: String,
    @SerializedName("display_title")
    val display_title: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("link")
    val link: Link,
    @SerializedName("mpaa_rating")
    val mpaa_rating: String,
    @SerializedName("multimedia")
    val multimedia: Multimedia,
    @SerializedName("opening_date")
    val opening_date: String,
    @SerializedName("publication_date")
    val publication_date: String,
    @SerializedName("summary_short")
    val summary_short: String
)
package ui.smartpro.data.model.response

import com.google.gson.annotations.SerializedName

data class NewResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("has_more")
    val has_more: Boolean,
    @SerializedName("num_results")
    val num_results: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("status")
    val status: String,
    val nextPage: Int? = null
)
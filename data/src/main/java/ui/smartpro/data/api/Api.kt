package ui.smartpro.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ui.smartpro.common.utils.Constants.Companion.API_KEY
import ui.smartpro.data.model.response.NewResponse

/**
 *  https://api.nytimes.com/svc/movies/v2/reviews/all.json?api-key=1J9DCbH2mBPH3gnJ4TsbWZ7nDSdvUBDQ
 */

interface Api {

    @GET("/svc/movies/v2/reviews/{type}.json")
    suspend fun getAllReviews(
        @Path("type") type: String = "all",
        @Query("api-key") apiKey: String = API_KEY
    ): NewResponse
}
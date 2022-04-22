package ui.smartpro.common.utils

import ui.smartpro.appselect.BuildConfig

class Constants {
    companion object {
        const val API_KEY = BuildConfig.NYNEWS_API_KEY
//        const val API_KEY = "1J9DCbH2mBPH3gnJ4TsbWZ7nDSdvUBDQ"
        const val BASE_URL = "https://api.nytimes.com/"
        const val searchTimeDelay = 10000L
        const val QUERY_PER_PAGE = 20
        const val CountryCode = "ru"
    }
}
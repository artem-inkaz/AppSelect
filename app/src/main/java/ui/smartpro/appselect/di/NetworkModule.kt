package ui.smartpro.appselect.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ui.smartpro.appselect.BuildConfig
import ui.smartpro.common.utils.Constants.Companion.readWriteTimeDelay
import ui.smartpro.common.utils.Constants.Companion.searchTimeDelay
import ui.smartpro.data.api.Api
import ui.smartpro.logging.Logger
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val TAG = "NY_NEWS"

    @Provides
    fun provideBaseUrl(): String {
        return "https://api.nytimes.com/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Logger.d(message)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(searchTimeDelay, TimeUnit.SECONDS)
            .readTimeout(readWriteTimeDelay, TimeUnit.MINUTES)
            .writeTimeout(readWriteTimeDelay, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gSon: Gson,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .validateEagerly(true)
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
}
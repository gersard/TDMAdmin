package cl.gerardomascayano.tdmadmin.core.di

import cl.gerardomascayano.tdmadmin.BuildConfig
import cl.gerardomascayano.tdmadmin.data.remote.network.ApiConstants
import cl.gerardomascayano.tdmadmin.data.remote.network.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient() =
        OkHttpClient.Builder()
            .apply {
                connectTimeout(10, TimeUnit.SECONDS)
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
                addInterceptor(
                    BasicAuthInterceptor(
                        BuildConfig.CLIENT_ID,
                        BuildConfig.SECRET_ID
                    )
                )

                if (BuildConfig.DEBUG) {
                    val logInterceptor = HttpLoggingInterceptor()
                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logInterceptor)
                }
            }.build()


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ApiConstants.URL_BASE)
        .client(okHttpClient)
        .build()


}
package cl.gerardomascayano.tdmadmin.core.di

import cl.gerardomascayano.tdmadmin.BuildConfig
import cl.gerardomascayano.tdmadmin.network.ApiConstants
import cl.gerardomascayano.tdmadmin.network.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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
                        "ck_fe6ed93b10c9d04fd184144a9e5042bed032bb68",
                        "cs_3a8772496fb677a891a576b03f78e76a5e9c81bb"
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
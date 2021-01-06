package cl.gerardomascayano.tdmadmin.data.remote.di

import cl.gerardomascayano.tdmadmin.data.remote.OrdersService
import cl.gerardomascayano.tdmadmin.data.repository.OrderWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object OrdersNetworkModule {

    @Provides
    fun providesOrdersService(retrofit: Retrofit): OrdersService =
        retrofit.create(OrdersService::class.java)

    @Provides
    fun providesOrderWrapper(): OrderWrapper = OrderWrapper()
}
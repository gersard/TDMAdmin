package cl.gerardomascayano.tdmadmin.data.remote.di

import cl.gerardomascayano.tdmadmin.data.remote.OrdersService
import cl.gerardomascayano.tdmadmin.data.remote.OrderWrapper
import cl.gerardomascayano.tdmadmin.data.remote.OrdersDataSource
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

    @Provides
    fun providesOrderDataSource(orderService: OrdersService) = OrdersDataSource(orderService)
}
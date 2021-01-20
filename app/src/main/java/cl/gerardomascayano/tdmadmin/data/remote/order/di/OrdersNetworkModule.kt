package cl.gerardomascayano.tdmadmin.data.remote.order.di

import cl.gerardomascayano.tdmadmin.data.remote.order.OrdersService
import cl.gerardomascayano.tdmadmin.data.remote.order.OrderMapper
import cl.gerardomascayano.tdmadmin.data.remote.order.OrdersDataSource
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteMapper
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
    fun providesOrderMapper(): OrderMapper = OrderMapper()

    @Provides
    fun providesOrderNoteMapper(): OrderNoteMapper = OrderNoteMapper()

    @Provides
    fun providesOrderDataSource(orderService: OrdersService) = OrdersDataSource(orderService)
}
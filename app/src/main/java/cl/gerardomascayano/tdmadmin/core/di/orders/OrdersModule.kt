package cl.gerardomascayano.tdmadmin.core.di.orders

import cl.gerardomascayano.tdmadmin.data.remote.OrdersDataSource
import cl.gerardomascayano.tdmadmin.data.remote.OrdersDataSourceImpl
import cl.gerardomascayano.tdmadmin.data.repository.OrdersRepository
import cl.gerardomascayano.tdmadmin.data.repository.OrdersRepositoryImpl
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class OrdersModule {

    @Binds
    abstract fun bindsUseCase(useCaseImpl: OrdersUseCaseImpl): OrdersUseCase

    @Binds
    abstract fun bindsRepository(repoImpl: OrdersRepositoryImpl): OrdersRepository

    @Binds
    abstract fun bindsRemoteDataSource(dataSourceImpl: OrdersDataSourceImpl): OrdersDataSource


}
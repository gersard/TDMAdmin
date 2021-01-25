package cl.gerardomascayano.tdmadmin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import cl.gerardomascayano.tdmadmin.core.GenericState
import cl.gerardomascayano.tdmadmin.data.remote.order.OrderUpdate
import cl.gerardomascayano.tdmadmin.data.remote.order.OrderMapper
import cl.gerardomascayano.tdmadmin.data.remote.order.OrdersDataSource
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.data.remote.network.ApiConstants
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteCreate
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteMapper
import cl.gerardomascayano.tdmadmin.domain.order.list.OrdersViewState
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNoteState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrdersDataSource,
    private val orderMapper: OrderMapper,
    private val orderNoteMapper: OrderNoteMapper
) : OrdersRepository {

    override fun getOrders(filterText: String): Flow<PagingData<Order>> {
        //TODO TEMPORAL !!!
        remoteDataSource.currentFilterText = filterText
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { remoteDataSource }
        ).flow.map { pagingDataNetwork ->
            pagingDataNetwork.map { orderResponse -> orderMapper.orderResponseToOrder(orderResponse) }
        }
    }

    override suspend fun getOrders(page: Int, filterText: String): OrdersViewState {
        return remoteDataSource.getOrders(page, filterText)?.let {
            if (it.isSuccessful) {
                OrdersViewState.Success(orderMapper.ordersResponseToOrder(it.body()!!))
            } else OrdersViewState.Error("Error en la carga de datos")
        } ?: kotlin.run { OrdersViewState.Error("Ha ocurrido un error. Comprueba tu conexión y vuelve a intentar") }
    }

    override suspend fun updateOrder(orderId: Int, status: String): GenericState {
        return remoteDataSource.updateOrder(OrderUpdate(orderId, status))?.let {
            GenericState.Success(orderMapper.orderResponseToOrder(it))
        } ?: kotlin.run { GenericState.Error("Error al actualizar orden") }
    }

    override suspend fun getOrderNotes(orderId: Int): OrderNoteState {
        val response = remoteDataSource.getOrderNotes(orderId)
        return if (response?.isSuccessful == true) {
            if (response.body() != null)
                OrderNoteState.Success(orderNoteMapper.orderNotesResponseToOrderNotesDomain(response.body()!!))
            else OrderNoteState.Error("Ha ocurrido un error inesperado")
        } else {
            OrderNoteState.Error("Ha ocurrido un error. Comprueba tu conexión y vuelve a intentar")
        }
    }

    override suspend fun createOrderNote(orderId: Int, note: String, customerNote: Boolean): GenericState {
        val response = remoteDataSource.createOrderNote(orderId, OrderNoteCreate(note, customerNote))
        return if (response?.isSuccessful == true) {
            if (response.body() != null) {
                GenericState.Success(orderNoteMapper.orderNoteResponseToOrderNoteDomain(response.body()!!))
            } else GenericState.Error("Ha ocurrido un error inesperado")
        } else {
            GenericState.Error("Ha ocurrido un error. Comprueba tu conexión y vuelve a intentar")
        }
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = ApiConstants.DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}
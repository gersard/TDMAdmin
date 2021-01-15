package cl.gerardomascayano.tdmadmin.domain.order.detail

import cl.gerardomascayano.tdmadmin.domain.order.Product

data class HeaderProducts(val title: String, val products: List<Product>) : Header(title)

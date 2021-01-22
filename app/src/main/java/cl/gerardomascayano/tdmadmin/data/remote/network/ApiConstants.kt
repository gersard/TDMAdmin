package cl.gerardomascayano.tdmadmin.data.remote.network

object ApiConstants {

    private const val API_VERSION = "v3/"
    const val URL_BASE = "https://www.tiempodemotos.cl/wp-json/wc/$API_VERSION"

    // METHOD'S
    const val METHOD_ORDERS = "orders"
    const val METHOD_NOTES = "notes"

    // PARAM'S
    const val PARAM_PAGE = "page"
    const val PARAM_SEARCH = "search"

    // PAGING
    const val DEFAULT_PAGE_SIZE = 15

}
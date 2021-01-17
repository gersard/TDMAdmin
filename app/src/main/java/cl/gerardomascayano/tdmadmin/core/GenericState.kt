package cl.gerardomascayano.tdmadmin.core


sealed class GenericState {

    data class Loading(val isLoading: Boolean) : GenericState()
    data class Error(val errorMessage: String) : GenericState()
    data class Success<T>(var data: T) : GenericState()


}
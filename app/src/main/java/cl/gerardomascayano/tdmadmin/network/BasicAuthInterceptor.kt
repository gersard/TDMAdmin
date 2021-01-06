package cl.gerardomascayano.tdmadmin.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(user: String, pass: String) : Interceptor {

    var credentials: String = Credentials.basic(user, pass)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authRequest = request.newBuilder()
            .header("Authorization", credentials)
            .build()
        return chain.proceed(authRequest)
    }
}
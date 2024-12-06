package com.example.citaproyect.network
import com.example.citaproyect.tablas.Usuario
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/api/v2/Usuarios")
    fun obtenerUsuarios():  Response<List<Usuario>> // Devuelve una lista de usuarios

    @POST("/api/v2/Usuarios")
    suspend fun insertUsuario(@Body usuario: Usuario): Response<Usuario>

}
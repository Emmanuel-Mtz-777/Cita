package com.example.citaproyect

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    // Función para obtener la lista de usuarios
    @GET("api/v2/Usuarios/Usuariosget") // Sin la barra inicial
    suspend fun obtenerUsuarios(): Response<List<Usuario>>  // Usamos suspend para que funcione con corutinas

    @GET("api/v2/Usuarios/Usuariosid/{usuarioId}")  // Endpoint dinámico que recibe el usuarioId
    suspend fun obtenerUsuarioPorId(@Path("usuarioId") usuarioId: String): Response<Usuario>

    // Función para insertar un usuario
    @POST("api/v2/Usuarios/Usuarios")  // Sin la barra inicial
    suspend fun insertUsuario(@Body usuario: Usuario): Response<Usuario>  // También suspend para ser asincrónica
}
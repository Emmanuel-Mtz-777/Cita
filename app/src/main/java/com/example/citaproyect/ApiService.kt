package com.example.citaproyect

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {

    // Función para obtener la lista de usuarios
    @GET("api/v2/Usuarios/Usuariosget") // Sin la barra inicial
    suspend fun obtenerUsuarios(): Response<List<Usuario>>  // Usamos suspend para que funcione con corutinas

    @GET("api/v2/Usuarios/Usuariosid/{usuarioId}")  // Endpoint dinámico que recibe el usuarioId
    suspend fun obtenerUsuarioPorId(@Path("usuarioId") usuarioId: String): Response<Usuario>

    @GET("api/v2/Usuarios/Usuariosid/{usuarioId}")  // Endpoint dinámico que recibe el usuarioId
    suspend fun obtenerUsuarioPorIdeditar(@Path("usuarioId") usuarioId: Int): Response<Usuario>

    // Función para insertar un usuario
    @POST("api/v2/Usuarios/Usuarios")  // Sin la barra inicial
    suspend fun insertUsuario(@Body usuario: Usuario): Response<Usuario>  // También suspend para ser asincrónica


        @PUT("api/v2/Usuarios/ActualizarUsuario/{usuarioId}")
    suspend fun updateUser(
        @Path("usuarioId") usuarioId: String,
        @Body user: Usuario
    ): Response<Usuario>


    @DELETE("api/v2/Usuarios/EliminarUsuario/{usuarioId}")  // Asegúrate de que esta URL sea la correcta según tu backend
    fun eliminarUsuario(@Path("usuarioId") usuarioId: String): Call<Void>
}


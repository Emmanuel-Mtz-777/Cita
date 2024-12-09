package com.example.citaproyect

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ServiceDao {

    // CRUD para Usuario
    @Insert
    suspend fun insertUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuarios ORDER BY IdUsuario DESC LIMIT 5")
    suspend fun getLastTenUsers(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE IdUsuario = :usuarioId LIMIT 1")
    suspend fun getUsuarioById(usuarioId: Int): Usuario?

    @Query("SELECT * FROM Usuarios ORDER BY IdUsuario DESC LIMIT 1")
    fun getLastUsuario(): Usuario?

    @Query("DELETE FROM Usuarios WHERE IdUsuario = :usuarioId")
    suspend fun deleteUserById(usuarioId: Int)
    @Query("SELECT * FROM usuarios")
    suspend fun getAllUsuarios(): List<Usuario>

    @Update
    suspend fun updateUsuario(usuario: Usuario)

    @Delete
    suspend fun deleteUsuario(usuario: Usuario)

    // CRUD para Evento
    @Insert
    suspend fun insertEvento(evento: Evento)

    @Query("SELECT * FROM eventos WHERE IdEvento = :idEvento")
    suspend fun getEventoById(idEvento: Int): Evento?

    @Update
    suspend fun updateEvento(evento: Evento)

    @Delete
    suspend fun deleteEvento(evento: Evento)

    // CRUD para Encuesta
    @Insert
    suspend fun insertEncuesta(encuesta: Encuesta)

    @Query("SELECT * FROM encuestas WHERE IdEncuesta = :idEncuesta")
    suspend fun getEncuestaById(idEncuesta: Int): Encuesta?

    @Update
    suspend fun updateEncuesta(encuesta: Encuesta)

    @Delete
    suspend fun deleteEncuesta(encuesta: Encuesta)

    // CRUD para Pregunta
    @Insert
    suspend fun insertPregunta(pregunta: Pregunta)

    @Query("SELECT * FROM preguntas WHERE IdPregunta = :idPregunta")
    suspend fun getPreguntaById(idPregunta: Int): Pregunta?

    @Update
    suspend fun updatePregunta(pregunta: Pregunta)

    @Delete
    suspend fun deletePregunta(pregunta: Pregunta)

    // CRUD para Opcion
    @Insert
    suspend fun insertOpcion(opcion: Opcion)

    @Query("SELECT * FROM opciones WHERE IdOpcion = :idOpcion")
    suspend fun getOpcionById(idOpcion: Int): Opcion?

    @Update
    suspend fun updateOpcion(opcion: Opcion)

    @Delete
    suspend fun deleteOpcion(opcion: Opcion)

    // CRUD para Respuesta
    @Insert
    suspend fun insertRespuesta(respuesta: Respuesta)

    @Query("SELECT * FROM respuestas WHERE IdRespuesta = :idRespuesta")
    suspend fun getRespuestaById(idRespuesta: Int): Respuesta?

    @Update
    suspend fun updateRespuesta(respuesta: Respuesta)

    @Delete
    suspend fun deleteRespuesta(respuesta: Respuesta)

    // CRUD para MensajeER (Mensajes Enviados y Recibidos)
    @Insert
    suspend fun insertMensajeER(mensajeER: MensajeER)

    @Query("SELECT * FROM mensajeER WHERE IdMensajeER = :idMensajeER")
    suspend fun getMensajeERById(idMensajeER: Int): MensajeER?

    @Update
    suspend fun updateMensajeER(mensajeER: MensajeER)

    @Delete
    suspend fun deleteMensajeER(mensajeER: MensajeER)

    // CRUD para Amigos
    @Insert
    suspend fun insertAmigos(amigos: Amigos)

    @Query("SELECT * FROM amigos WHERE IdAmigo = :idAmigo")
    suspend fun getAmigosById(idAmigo: Int): Amigos?

    @Update
    suspend fun updateAmigos(amigos: Amigos)

    @Delete
    suspend fun deleteAmigos(amigos: Amigos)

    // CRUD para Grupo
    @Insert
    suspend fun insertGrupo(grupo: Grupo)

    @Query("SELECT * FROM grupos WHERE IdGrupo = :idGrupo")
    suspend fun getGrupoById(idGrupo: Int): Grupo?

    @Update
    suspend fun updateGrupo(grupo: Grupo)

    @Delete
    suspend fun deleteGrupo(grupo: Grupo)

    // CRUD para MensajeG (Mensajes en Grupos)
    @Insert
    suspend fun insertMensajeG(mensajeG: MensajeG)

    @Query("SELECT * FROM mensajeG WHERE IdMensajeG = :idMensajeG")
    suspend fun getMensajeGById(idMensajeG: Int): MensajeG?

    @Update
    suspend fun updateMensajeG(mensajeG: MensajeG)

    @Delete
    suspend fun deleteMensajeG(mensajeG: MensajeG)

    // CRUD para MiembroGrupo
    @Insert
    suspend fun insertMiembroGrupo(miembroGrupo: MiembroGrupo)

    @Query("SELECT * FROM miembrosGrupo WHERE IdMiembro = :idMiembro")
    suspend fun getMiembroGrupoById(idMiembro: Int): MiembroGrupo?

    @Update
    suspend fun updateMiembroGrupo(miembroGrupo: MiembroGrupo)

    @Delete
    suspend fun deleteMiembroGrupo(miembroGrupo: MiembroGrupo)
}

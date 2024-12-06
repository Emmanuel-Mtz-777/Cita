package com.example.citaproyect

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val IdUsuario: Int = 0,
    val nombre: String,
    val email: String,
    val descripcion: String? = null, // Puede ser nulo
    val foto: String? = null,         // Puede ser nulo
    val password: String
)


@Entity(
    tableName = "respuestas",
    foreignKeys = [
        ForeignKey(
            entity = Pregunta::class, // Referencia a la entidad Pregunta
            parentColumns = ["IdPregunta"], // Columna primaria en Pregunta
            childColumns = ["IdPregunta"], // Columna en Respuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las respuestas cuando se elimina la pregunta
        ),
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Columna en Respuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las respuestas cuando se elimina el usuario
        ),
        ForeignKey(
            entity = Opcion::class, // Referencia a la entidad Opcion (si la tienes definida)
            parentColumns = ["IdOpcion"], // Columna primaria en Opcion
            childColumns = ["IdOpcion"], // Columna en Respuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las respuestas cuando se elimina la opción
        )
    ]
)
data class Respuesta(
    @PrimaryKey(autoGenerate = true) val IdRespuesta: Int = 0, // Llave primaria para la tabla
    val IdPregunta: Int, // Clave foránea que hace referencia a la tabla Pregunta
    val IdOpcion: Int, // Clave foránea que hace referencia a la tabla Opcion
    val IdUsuario: Int, // Clave foránea que hace referencia a la tabla Usuario
    val FechaRespuesta: String // Fecha en que se dio la respuesta (puedes usar LocalDate si prefieres)
)


@Entity(
    tableName = "preguntas",
    foreignKeys = [
        ForeignKey(
            entity = Encuesta::class, // Referencia a la entidad Encuesta
            parentColumns = ["IdEncuesta"], // Columna primaria en Encuesta
            childColumns = ["IdEncuesta"], // Columna en Pregunta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las preguntas cuando se elimina la encuesta
        )
    ]
)
data class Pregunta(
    @PrimaryKey(autoGenerate = true) val IdPregunta: Int = 0, // Llave primaria para la tabla
    val IdEncuesta: Int, // Clave foránea que hace referencia al IdEncuesta de la tabla Encuesta
    val TextoPregunta: String // Texto de la pregunta
)

@Entity(
    tableName = "opciones",
    foreignKeys = [
        ForeignKey(
            entity = Pregunta::class, // Referencia a la entidad Pregunta
            parentColumns = ["IdPregunta"], // Columna primaria en Pregunta
            childColumns = ["IdPregunta"], // Columna en Opcion que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las opciones cuando se elimina la pregunta
        )
    ]
)
data class Opcion(
    @PrimaryKey(autoGenerate = true) val IdOpcion: Int = 0, // Llave primaria para la tabla
    val IdPregunta: Int, // Clave foránea que hace referencia al IdPregunta de la tabla Pregunta
    val TextoOpcion: String // Texto de la opción de respuesta
)


@Entity(
    tableName = "miembrosGrupo",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Clave foránea que hace referencia al usuario
            onDelete = ForeignKey.CASCADE // Elimina el miembro si el usuario es eliminado
        ),
        ForeignKey(
            entity = Grupo::class, // Referencia a la entidad Grupo
            parentColumns = ["IdGrupo"], // Columna primaria en Grupo
            childColumns = ["IdGrupo"], // Clave foránea que hace referencia al grupo
            onDelete = ForeignKey.CASCADE // Elimina el miembro si el grupo es eliminado
        )
    ]
)
data class MiembroGrupo(
    @PrimaryKey(autoGenerate = true) val IdMiembro: Int = 0, // Llave primaria para la tabla
    val IdUsuario: Int, // Clave foránea que hace referencia a la tabla Usuario
    val IdGrupo: Int, // Clave foránea que hace referencia a la tabla Grupo
    val fechaIngreso: String // Fecha de ingreso del usuario al grupo
)



@Entity(
    tableName = "mensajeG",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Clave foránea que hace referencia al usuario
            onDelete = ForeignKey.CASCADE // Elimina el mensaje si el usuario es eliminado
        ),
        ForeignKey(
            entity = Grupo::class, // Referencia a la entidad Grupo
            parentColumns = ["IdGrupo"], // Columna primaria en Grupo
            childColumns = ["IdGrupo"], // Clave foránea que hace referencia al grupo
            onDelete = ForeignKey.CASCADE // Elimina el mensaje si el grupo es eliminado
        )
    ]
)
data class MensajeG(
    @PrimaryKey(autoGenerate = true) val IdMensajeG: Int = 0, // Llave primaria para la tabla
    val IdUsuario: Int, // Clave foránea que hace referencia a la tabla Usuario
    val IdGrupo: Int, // Clave foránea que hace referencia a la tabla Grupo
    val mensaje: String, // Contenido del mensaje
    val fechaHora: String // Fecha y hora en que se envió el mensaje (puedes usar LocalDateTime con TypeConverter)
)


@Entity(
    tableName = "mensajeER",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["emisorUsuarioId"], // Clave foránea que actúa como emisor
            onDelete = ForeignKey.CASCADE // Elimina los mensajes si el usuario emisor es eliminado
        ),
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["receptorUsuarioId"], // Clave foránea que actúa como receptor
            onDelete = ForeignKey.CASCADE // Elimina los mensajes si el usuario receptor es eliminado
        )
    ]
)
data class MensajeER(
    @PrimaryKey(autoGenerate = true) val IdMensajeER: Int = 0, // Llave primaria para la tabla
    val emisorUsuarioId: Int, // Clave foránea para el emisor, referencia a IdUsuario
    val receptorUsuarioId: Int, // Clave foránea para el receptor, referencia a IdUsuario
    val mensaje: String, // Contenido del mensaje
    val fechaHora: String // Fecha y hora en que se envió el mensaje (puedes usar LocalDateTime con TypeConverter)
)


@Entity(tableName = "grupos")
data class Grupo(
    @PrimaryKey(autoGenerate = true) val IdGrupo: Int = 0, // Llave primaria para la tabla
    val nombre: String, // Nombre del grupo
    val descripcion: String, // Descripción del grupo
    val imagen: String? = null // Imagen del grupo (opcional)
)
@Entity(
    tableName = "eventos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Columna en Evento que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina los eventos cuando se elimina el usuario
        )
    ]
)
data class Evento(
    @PrimaryKey(autoGenerate = true) val IdEvento: Int = 0, // Llave primaria para la tabla
    val Nombre: String, // Nombre del evento
    val Descripcion: String, // Descripción del evento
    val Lugar: String, // Lugar del evento
    val Fecha: String, // Fecha del evento (también podría ser LocalDate con TypeConverter)
    val IdUsuario: Int // Clave foránea que hace referencia al IdUsuario de la tabla Usuario
)

@Entity(
    tableName = "encuestas",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["IdUsuario"], // Columna en Encuesta que actúa como clave foránea
            onDelete = ForeignKey.CASCADE // Elimina las encuestas cuando se elimina el usuario
        )
    ]
)
data class Encuesta(
    @PrimaryKey(autoGenerate = true) val IdEncuesta: Int = 0, // Llave primaria para la tabla
    val Titulo: String, // Título de la encuesta
    val Descripcion: String, // Descripción de la encuesta
    val FechaCreacion: String, // Fecha de creación de la encuesta (podrías usar LocalDate con TypeConverter)
    val IdUsuario: Int // Clave foránea que hace referencia al IdUsuario de la tabla Usuario
)


@Entity(
    tableName = "amigos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["usuarioPrincipal"], // Clave foránea que actúa como usuario principal
            onDelete = ForeignKey.CASCADE // Elimina las relaciones si el usuario principal es eliminado
        ),
        ForeignKey(
            entity = Usuario::class, // Referencia a la entidad Usuario
            parentColumns = ["IdUsuario"], // Columna primaria en Usuario
            childColumns = ["usuarioAmigo"], // Clave foránea que actúa como usuario amigo
            onDelete = ForeignKey.CASCADE // Elimina las relaciones si el usuario amigo es eliminado
        )
    ]
)
data class Amigos(
    @PrimaryKey(autoGenerate = true) val IdAmigo: Int = 0, // Llave primaria para la tabla
    val usuarioPrincipal: Int, // Clave foránea para el usuario principal, referencia a IdUsuario
    val usuarioAmigo: Int, // Clave foránea para el usuario amigo, referencia a IdUsuario
    val fecha: String // Fecha en que se estableció la relación de amistad
)
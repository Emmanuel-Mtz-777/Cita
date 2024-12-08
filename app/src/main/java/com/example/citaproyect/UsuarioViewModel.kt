package com.example.citaproyect

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsuarioViewModel : ViewModel() {
    private val _usuarioId = MutableLiveData<String?>()
    val usuarioId: LiveData<String?> = _usuarioId

    // Función para actualizar el usuarioId
    fun setUsuarioId(id: String) {
        _usuarioId.value = id
    }
}

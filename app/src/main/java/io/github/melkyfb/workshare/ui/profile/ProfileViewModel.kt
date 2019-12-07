package io.github.melkyfb.workshare.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Perfil ainda não implementado"
    }
    val text: LiveData<String> = _text
}
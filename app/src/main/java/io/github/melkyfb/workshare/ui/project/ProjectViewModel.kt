package io.github.melkyfb.workshare.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjectViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Gerenciamento de Projetos"
    }
    val text: LiveData<String> = _text
}
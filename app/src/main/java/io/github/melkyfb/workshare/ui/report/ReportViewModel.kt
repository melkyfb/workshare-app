package io.github.melkyfb.workshare.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Relatorios ainda não implementado"
    }
    val text: LiveData<String> = _text
}
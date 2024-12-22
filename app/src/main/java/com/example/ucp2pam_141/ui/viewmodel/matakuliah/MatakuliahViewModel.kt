package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucp2pam_141.repository.RepositoryMatakuliah
import com.example.ucp2pam_141.ui.viewmodel.dosen.FormErrorState

class MatakuliahViewModel(private val repositoryMatakuliah: RepositoryMatakuliah) : ViewModel() {

    var uiState by mutableStateOf(MatakuliahUIState())

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            DosenPengampu = if (event.DosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

}


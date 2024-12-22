package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucp2pam_141.repository.RepositoryMatakuliah

class MatakuliahViewModel(private val repositoryMatakuliah: RepositoryMatakuliah) : ViewModel() {

    var uiState by mutableStateOf(MatakuliahUIState())

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }
}
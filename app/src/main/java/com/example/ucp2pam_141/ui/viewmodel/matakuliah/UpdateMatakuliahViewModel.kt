package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam_141.repository.RepositoryMatakuliah
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah
) : ViewModel() {
    var updateUiState by mutableStateOf(MatakuliahUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiMatakuliahUpdate.KODE])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMatakuliah.getMatakuliah(_kode)
                .filterNotNull()
                .first()
                .toUiStateMatakuliah()
        }
    }

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        updateUiState = updateUiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }



}
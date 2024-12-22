package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ucp2pam_141.repository.RepositoryMatakuliah

class UpdateMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah
) : ViewModel() {
    var updateUiState by mutableStateOf(MatakuliahUIState())
        private set
}
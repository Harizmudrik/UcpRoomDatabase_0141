package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object PenyediaViewModelMatkul {
    val Factory = viewModelFactory {
        initializer {
            MatakuliahViewModel(
                krsApp().containerApp.repositoryMatakuliah
            )
        }
        initializer {
            HomeMatkulViewModel(
                krsApp().containerApp.repositoryMatakuliah
            )
        }

        initializer {
            DetailMatakuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatakuliah
            )
        }

        initializer {
            UpdateMatakuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatakuliah
            )
        }
    }
}
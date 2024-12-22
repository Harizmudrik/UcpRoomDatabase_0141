package com.example.ucp2pam_141.ui.viewmodel.dosen

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object PenyediaViewModelDosen {
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }
        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            DetailDosenViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryDosen
            )
        }


    }
}

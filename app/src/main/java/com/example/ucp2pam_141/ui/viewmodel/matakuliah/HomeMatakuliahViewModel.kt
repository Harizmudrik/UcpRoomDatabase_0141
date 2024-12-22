package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam_141.data.entity.Matakuliah
import com.example.ucp2pam_141.repository.RepositoryMatakuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

// ViewModel untuk halaman utama mata kuliah
class HomeMatkulViewModel(
    private val repositoryMatakuliah: RepositoryMatakuliah
) : ViewModel() {

    // StateFlow untuk mengelola state UI halaman utama mata kuliah
    val homeUiState: StateFlow<HomeUiState> = repositoryMatakuliah.getAllMatakuliah() // Mengambil semua data mata kuliah
        .filterNotNull() // Mengabaikan nilai null dari aliran data
        .map {
            // Mengonversi daftar mata kuliah menjadi state UI
            HomeUiState(
                listMatakuliah = it.toList(), // Data list mata kuliah
                isLoading = false, // Status loading selesai
            )
        }
        .onStart {
            // Menangani status awal saat data mulai dimuat
            emit(HomeUiState(isLoading = true)) // Emit state dengan loading aktif
            delay(900) // Menunda selama 900ms untuk memberikan efek loading
        }
        .catch {
            // Menangani error dan mengubah state ke status error
            emit(
                HomeUiState(
                    isLoading = false, // Loading dihentikan
                    isError = true, // Tandai adanya error
                    errorMessage = it.message ?: "Terjadi Kesalahan" // Pesan error default
                )
            )
        }
        .stateIn(
            scope = viewModelScope, // Coroutine scope yang terkait dengan lifecycle ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Aliran aktif selama 5000ms setelah tidak ada subscriber
            initialValue = HomeUiState( // Nilai awal state
                isLoading = true, // Mulai dengan status loading
            )
        )
}

// Data class untuk merepresentasikan state UI di halaman utama
data class HomeUiState(
    val listMatakuliah: List<Matakuliah> = listOf(), // Daftar mata kuliah
    val isLoading: Boolean = false, // Status loading
    val isError: Boolean = false, // Status error
    val errorMessage: String = " " // Pesan error (kosong secara default)
)
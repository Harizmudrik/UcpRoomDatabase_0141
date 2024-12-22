package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam_141.data.entity.Matakuliah
import com.example.ucp2pam_141.repository.RepositoryMatakuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah,
) : ViewModel() {

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiMatakuliahDetail.KODE])

    val detailMatakuliahUIState: StateFlow<DetailMatakuliahUiState> = repositoryMatakuliah.getMatakuliah(_kode)
        .filterNotNull()
        .map {
            DetailMatakuliahUiState(
                detailMatakuliahUiEvent = it.toDetailMatakuliahUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailMatakuliahUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailMatakuliahUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue =
            DetailMatakuliahUiState(isLoading = true)
        )
    fun deleteMatakuliah() {
        detailMatakuliahUIState.value.detailMatakuliahUiEvent.toMatakuliahEntity().let {
            viewModelScope.launch {
                repositoryMatakuliah.deleteMatakuliah(it)
            }
        }
    }
}

data class DetailMatakuliahUiState(
    val detailMatakuliahUiEvent: MatakuliahEvent = MatakuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailMatakuliahUiEvent == MatakuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailMatakuliahUiEvent != MatakuliahEvent()
}

// Memindahkan data dari Entity ke UI
fun Matakuliah.toDetailMatakuliahUiEvent(): MatakuliahEvent {
    return MatakuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        DosenPengampu = DosenPengampu
    )
}
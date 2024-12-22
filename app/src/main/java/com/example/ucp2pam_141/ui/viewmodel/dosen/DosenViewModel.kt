package com.example.ucp2pam_141.ui.viewmodel.dosen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam_141.repository.RepositoryDosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryDosen: RepositoryDosen) : ViewModel() {

    var uiState by mutableStateOf(DosenUIState())

    fun updateState(dosenEvent: DosenEvent) {
        uiState = uiState.copy(
            dosenEvent = dosenEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",

            )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDosen.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil disimpan",
                        dosenEvent = DosenEvent(), //Reset input form
                        isEntryValid = FormErrorState() //Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid, Periksa kembali data Anda"
            )
        }
    }

    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }

    val DosenUIState: StateFlow<DosenUIState> = repositoryDosen.getAllDosen()
        .filterNotNull() // Memfilter data yang tidak null
        .map {
            DosenUIState(
                listDosen = it.toList(), // Konversi data ke list
                isLoading = false, // Indikasi data sudah dimuat
            )
        }
        .onStart {
            emit(DosenUIState(isLoading = true)) // Emit loading state saat mulai
            delay(900) // Delay untuk simulasi
        }
        .catch {
            emit(
                DosenUIState(
                    isLoading = false, // Menonaktifkan loading
                    isError = true, // Tanda error terjadi
                    errorState = it.message ?: "Terjadi Kesalahan" // Pesan error
                )
            )
        }
        .stateIn(
            scope = viewModelScope, // Scope coroutine ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Mulai berbagi aliran data
            initialValue = DosenUIState(
                isLoading = true, // State awal loading
            )
        )




}

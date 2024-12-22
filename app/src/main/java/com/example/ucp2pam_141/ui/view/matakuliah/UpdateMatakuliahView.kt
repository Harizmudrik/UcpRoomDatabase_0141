package com.example.ucp2pam_141.ui.view.matakuliah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.ucp2pam_141.ui.customwidget.TopAppBar
import com.example.ucp2pam_141.ui.viewmodel.dosen.HomeDosenViewModel
import com.example.ucp2pam_141.ui.viewmodel.dosen.PenyediaViewModelDosen
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.PenyediaViewModelMatkul
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.UpdateMatakuliahViewModel


@Composable
fun UpdateMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMatakuliahViewModel = viewModel(factory = PenyediaViewModelMatkul.Factory), // Inisialisasi ViewModel
    viewModelDosen: HomeDosenViewModel = viewModel(factory = PenyediaViewModelDosen.Factory),
) {
    val uiState = viewModel.updateUiState // Ambil UI State dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar State
    val coroutineScope = rememberCoroutineScope()


    // Observasi Perubahan SnackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        println("LaunchedEffect triggered")
        uiState.snackBarMessage?.let { message ->
            println("Snackbar message recieved: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage() // Reset pesan setelah ditampilkan
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Tempatkan snackbar di Scaffold
        topBar = {
            TopAppBar(
                judul = "Edit Matakuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = Modifier
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Pastikan padding scaffold digunakan
                .padding(16.dp)
        ) {
            // 1st Body
            InsertBodyMatakuliah(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // Update state di ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            withContext(Dispatchers.Main) {
                                onNavigate() // Navigasi di main thread
                            }
                        }
                    }
                }
            )
        }
    }
}
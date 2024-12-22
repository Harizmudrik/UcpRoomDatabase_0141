package com.example.ucp2pam_141.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.MatakuliahUIState
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.MatakuliahViewModel
import kotlinx.coroutines.launch
import com.example.ucp2pam_141.ui.customwidget.TopAppBar
import com.example.ucp2pam_141.ui.navigation.AlamatNavigasi
import com.example.ucp2pam_141.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2pam_141.ui.viewmodel.dosen.PenyediaViewModelDosen
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.MatakuliahEvent
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.PenyediaViewModelMatkul


@Composable
fun InsertMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = PenyediaViewModelMatkul.Factory),

    ) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah"
            )
            InsertBodyMatakuliah(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMatakuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahEvent) -> Unit,
    uiState: MatakuliahUIState,
    onClick: () -> Unit
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick=onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}


@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange: (MatakuliahEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    DosenViewModel: DosenViewModel = viewModel(factory = PenyediaViewModelDosen.Factory)
) {
    val DosenUIState by DosenViewModel.DosenUIState.collectAsState()
    val listDosen = DosenUIState.listDosen.map{it.nama}
    val semester = listOf("Genap", "Ganjil")
    val jenis = listOf("Wajib", "Peminatan")

    Column(modifier = modifier.fillMaxWidth().padding(20.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kode = it))
            },
            label = { Text("Kode") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukan kode") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.nama,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukan nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.sks,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukan SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(9.dp))
        Text(text = "Semester")
        Row(modifier = Modifier.fillMaxWidth()) {
            semester.forEach { semester ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.semester == semester,
                        onClick = { onValueChange(matakuliahEvent.copy(semester = semester)) }
                    )
                    Text(text = semester)
                }
            }
        }
        Text(
            text = errorState.semester ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(9.dp))
        Text(text = "Jenis")
        Row(modifier = Modifier.fillMaxWidth()) {
            jenis.forEach { j ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.jenis == j,
                        onClick = { onValueChange(matakuliahEvent.copy(jenis = j)) }
                    )
                    Text(text = j)
                }
            }
        }
        Text(
            text = errorState.jenis ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(9.dp))

        DropdownMenuField(
            label = "Nama Dosen Pengampu",
            options = listDosen,
            selectedOption = matakuliahEvent.DosenPengampu,
            onOptionSelected = {
                    selectedDosen -> onValueChange(matakuliahEvent.copy(DosenPengampu = selectedDosen))
            },
            isError = errorState.DosenPengampu != null,
            errorMessage = errorState.DosenPengampu
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) } // Mengatur status drop-down
    var currentSelection by remember { mutableStateOf(selectedOption) } // Menyimpan pilihan saat ini

    Column {
        OutlinedTextField(
            value = currentSelection,
            onValueChange = {}, // Tidak memungkinkan input manual
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                androidx.compose.material3.IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
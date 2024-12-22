package com.example.ucp2pam_141.ui.view.matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam_141.data.entity.Matakuliah
import kotlinx.coroutines.launch
import com.example.ucp2pam_141.ui.customwidget.TopAppBar
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.PenyediaViewModelMatkul
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.HomeMatkulViewModel
import com.example.ucp2pam_141.ui.viewmodel.matakuliah.HomeUiState

@Composable
fun HomeMatakuliahView(
    // ViewModel untuk data matakuliah
    viewModel: HomeMatkulViewModel = viewModel(factory = PenyediaViewModelMatkul.Factory),
    onBack: () -> Unit = {},
    onDetailClick: (String) -> Unit, // Klik detail menerima parameter String (nim),
    onAddMatakuliah: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Matakuliah",
                showBackButton = false,
                onBack =  onBack,
                modifier = modifier
            )
        },
        floatingActionButton = { // Tombol tambah Matakuliah
            FloatingActionButton(
                onClick = onAddMatakuliah,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Matakuliah",
                )
            }
        }
    ) { innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyHomeMatakuliahView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeMatakuliahView(
    homeUiState: HomeUiState, // State UI dari ViewModel
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope() // Scope untuk menjalankan coroutine
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar State
    when {
        homeUiState.isLoading -> {
            // Menampilkan Indikator Loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Menampilkan indikator loading
            }
        }
        homeUiState.isError -> {
            // Menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) // Tampilkan Snackbar
                    }
                }
            }
        }

        homeUiState.listMatakuliah.isEmpty() -> {
            // Menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data matakuliah.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            // Menampilkan daftar matakuliah
            ListMatakuliah(
                listMatakuliah = homeUiState.listMatakuliah,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListMatakuliah(
    listMatakuliah: List<Matakuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn( // Kolom yang dapat digulirkan
        modifier = modifier
    ) {
        items(
            items = listMatakuliah,
            itemContent = { matakuliah ->
                CardMatkuliah(
                    matakuliah = matakuliah, // Setiap item menjadi kartu Matakuliah
                    onClick = { onClick(matakuliah.kode)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMatkuliah(
    matakuliah: Matakuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matakuliah.kode,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matakuliah.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matakuliah.sks,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Face, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matakuliah.semester,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matakuliah.jenis,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matakuliah.DosenPengampu,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
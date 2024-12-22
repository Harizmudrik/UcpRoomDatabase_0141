package com.example.ucp2pam_141.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ucp2pam_141.repository.RepositoryMatakuliah

class DetailMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah,
) : ViewModel() {

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiMatakuliahDetail.KODE])


}
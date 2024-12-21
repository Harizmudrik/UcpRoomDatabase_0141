package com.example.ucp2pam_141.repository

import com.example.ucp2pam_141.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMatakuliah {
    suspend fun insertMatakuliah(matakuliah: Matakuliah)

    fun getAllMatakuliah(): Flow<List<Matakuliah>>


    fun getMatakuliah(kode: String): Flow<Matakuliah> // Mengambil data matakuliah berdasarkan KODE


    suspend fun deleteMatakuliah(matakuliah: Matakuliah) // Menghapus data Matakuliah


    suspend fun updateMatakuliah(matakuliah: Matakuliah) // Memperbarui data matakuliah
}
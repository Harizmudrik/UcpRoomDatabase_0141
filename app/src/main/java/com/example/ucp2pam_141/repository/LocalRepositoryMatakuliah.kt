package com.example.ucp2pam_141.repository

import com.example.ucp2pam_141.data.dao.MatakuliahDao
import com.example.ucp2pam_141.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMatakuliah (
    private val matakuliahDao: MatakuliahDao
) : RepositoryMatakuliah {
    override suspend fun insertMatakuliah(matakuliah: Matakuliah) {
        matakuliahDao.insertMatakuliah(matakuliah)
    }

    override fun getAllMatakuliah(): Flow<List<Matakuliah>> {
        return matakuliahDao.getAllMatakuliah()
    }

    override fun getMatakuliah(kode: String): Flow<Matakuliah> {
        return matakuliahDao.getMatakuliah(kode)
    }

    override suspend fun deleteMatakuliah(matakuliah: Matakuliah) {
        matakuliahDao.deleteMatakuliah(matakuliah) // Memanggil fungsi DAO untuk mengahpus data mahasiswa dari tabel
    }

    override suspend fun updateMatakuliah(matakuliah: Matakuliah) { // Memperbarui data mahasiswa
        matakuliahDao.updateMatakuliah(matakuliah) // Memanggil fungsi DAO untuk memperbarui data mahasiswa dari tabel
    }
}


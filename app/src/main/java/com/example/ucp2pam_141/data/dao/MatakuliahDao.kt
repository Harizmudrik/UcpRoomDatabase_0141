package com.example.ucp2pam_141.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam_141.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MatakuliahDao {
    @Query("select * from matakuliah")
    fun getAllMatakuliah() : Flow<List<Matakuliah>>

    // get matakuliah
    @Query("SELECT * FROM matakuliah WHERE kode = :kode")
    fun getMatakuliah(kode: String): Flow<Matakuliah> // Data diambil berdasarkan NIM

    // Delete matakuliah
    @Delete
    suspend fun deleteMatakuliah(matakuliah: Matakuliah) // Suspend digunakan dengan coroutine

    // Update matakuliah
    @Update
    suspend fun updateMatakuliah(matakuliah: Matakuliah) // Memperbarui data di tabel

    @Insert
    suspend fun insertMatakuliah(
        matakuliah: Matakuliah
    )
}
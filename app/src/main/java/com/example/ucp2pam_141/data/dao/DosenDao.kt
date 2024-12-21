package com.example.ucp2pam_141.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ucp2pam_141.data.entity.Dosen
import kotlinx.coroutines.flow.Flow


@Dao
interface DosenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertDosen(dosen: Dosen)

    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>

    @Query("SELECT * FROM dosen WHERE nidn = :nidn")
    fun getDosen(nidn: String): Flow<Dosen>
}
package com.example.ucp2pam_141.repository

import com.example.ucp2pam_141.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {

    fun getAllDosen(): Flow<List<Dosen>>
    fun getDosen(nidn: String): Flow<Dosen>
    suspend fun insertDosen(dosen: Dosen)

}
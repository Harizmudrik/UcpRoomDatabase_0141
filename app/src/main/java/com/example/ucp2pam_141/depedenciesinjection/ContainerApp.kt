package com.example.ucp2pam_141.depedenciesinjection

import android.content.Context
import com.example.ucp2pam_141.data.database.KrsDatabase
import com.example.ucp2pam_141.repository.LocalRepositoryDosen
import com.example.ucp2pam_141.repository.LocalRepositoryMatakuliah
import com.example.ucp2pam_141.repository.RepositoryDosen
import com.example.ucp2pam_141.repository.RepositoryMatakuliah

interface InterfaceContainerApp {
    val repositoryMatakuliah: RepositoryMatakuliah
    val repositoryDosen: RepositoryDosen
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    override val repositoryMatakuliah: RepositoryMatakuliah by lazy {
        LocalRepositoryMatakuliah(KrsDatabase.getDatabase(context).matakuliahDao())
    }

    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(KrsDatabase.getDatabase(context).dosenDao())
    }
}
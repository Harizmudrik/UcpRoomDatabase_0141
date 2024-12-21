package com.example.ucp2pam_141.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dosen")
class Dosen (
    @PrimaryKey
    val nidn: String,
    val nama: String,
    val jenisKelamin: String,
)
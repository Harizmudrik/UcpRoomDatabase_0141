package com.example.ucp2pam_141.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home_menu"
}

object DestinasiDosen : AlamatNavigasi {
    override val route = "dosen"
}
object DestinasiInsertMataKuliah : AlamatNavigasi{
    override val route: String = "insert_matakuliah"
}
object DestinasiDosenDetail : AlamatNavigasi {
    override val route = "dosendetail"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}

object DestinasiMatakuliah : AlamatNavigasi {
    override val route = "matakuliah"
}

object DestinasiMatakuliahDetail : AlamatNavigasi {
    override val route = "detail"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiMatakuliahUpdate : AlamatNavigasi {
    override val route = "update"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}
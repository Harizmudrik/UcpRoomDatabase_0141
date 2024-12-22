package com.example.ucp2pam_141.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2pam_141.ui.view.dosen.DestinasiDosenInsert
import com.example.ucp2pam_141.ui.view.dosen.DetailDosenView
import com.example.ucp2pam_141.ui.view.dosen.HomeDosenView
import com.example.ucp2pam_141.ui.view.dosen.InsertDosenView
import com.example.ucp2pam_141.ui.view.home.HomeView
import com.example.ucp2pam_141.ui.view.matakuliah.DetailMatakuliahView
import com.example.ucp2pam_141.ui.view.matakuliah.HomeMatakuliahView
import com.example.ucp2pam_141.ui.view.matakuliah.InsertMatakuliahView
import com.example.ucp2pam_141.ui.view.matakuliah.UpdateMatakuliahView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route // Starting screen is HomeMhsView
    ) {
        composable(route = DestinasiHome.route) {
            HomeView(
                onDosenClick = {
                    navController.navigate(DestinasiDosen.route)
                },
                onMataKuliahClick = {
                    navController.navigate(DestinasiMatakuliah.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiDosen.route) {
            HomeDosenView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDosenDetail.route}/$nidn")
                    println("PengelolaHalaman = $nidn")
                },
                onBack = { navController.popBackStack() },
                onAddDosen = {
                    navController.navigate(DestinasiDosenInsert.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDosenInsert.route
        ) {
            InsertDosenView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(
            DestinasiDosenDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDosenDetail.NIDN) {
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(DestinasiDosenDetail.NIDN)
            nidn?.let { nidn ->
                DetailDosenView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }


        composable(
            route = DestinasiMatakuliah.route
        ) {
            HomeMatakuliahView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiMatakuliahDetail.route}/$kode")
                },
                onAddMatakuliah = {
                    navController.navigate(DestinasiInsertMataKuliah.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            DestinasiInsertMataKuliah.route
        ){
            InsertMatakuliahView(
                onBack = {
                    navController.popBackStack() // Return to previous screen
                },
                onNavigate = {
                    navController.popBackStack() // Go back after insert
                },
                modifier = modifier
            )
        }

        composable(
            DestinasiMatakuliahDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiMatakuliahDetail.KODE) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val kode = backStackEntry.arguments?.getString(DestinasiMatakuliahDetail.KODE)
            kode?.let { kode ->
                DetailMatakuliahView(
                    onBack = {
                        navController.popBackStack() // Return to previous screen
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiMatakuliahUpdate.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack() // Go back after deletion
                    }
                )
            }
        }

        composable(
            DestinasiMatakuliahUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiMatakuliahUpdate.KODE) {
                    type = NavType.StringType
                }
            )
        ) {

            UpdateMatakuliahView(
                onBack = {
                    navController.popBackStack() // Return to previous screen
                },
                onNavigate = {
                    navController.popBackStack() // Go back after update
                },
                modifier = modifier
            )
        }
    }
}
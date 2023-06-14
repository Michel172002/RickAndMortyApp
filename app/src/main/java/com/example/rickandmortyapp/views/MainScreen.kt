package com.example.rickandmortyapp.views

import HomeScreen
import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyapp.viewmodels.MainScreenUiState
import com.example.rickandmortyapp.viewmodels.RickAndMortyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    rickAndMortyViewModel: RickAndMortyViewModel = viewModel(),
){
    val navController = rememberNavController()

    val mainScreenUiState: State<MainScreenUiState> = rickAndMortyViewModel.mainScreenUiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = mainScreenUiState.value.screenName)
                },
                navigationIcon = {
                    if(mainScreenUiState.value.screenName == "characterScreen") {
                        IconButton(onClick = { rickAndMortyViewModel.ReturnHomeScreen(navController) }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Icon Return"
                            )
                        }
                    }
                }
            )
        }
    ) {
        NavHost(navController = navController, startDestination = "homeScreen"){
            composable("homeScreen"){
                HomeScreen(navController = navController, rickAndMortyViewModel = rickAndMortyViewModel)
            }
            composable("characterScreen"){
                CharacterScreen(navController = navController, rickAndMortyViewModel = rickAndMortyViewModel)
            }
        }
    }
}

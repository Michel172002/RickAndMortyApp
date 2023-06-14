package com.example.rickandmortyapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.rickandmortyapp.data.Character
import com.example.rickandmortyapp.data.CharactersList
import com.example.rickandmortyapp.network.RickAndMortyApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RickAndMortyViewModel: ViewModel() {
    sealed interface RickAndMortyUiState {
        data class Success(val characters: CharactersList): RickAndMortyUiState
        object Error: RickAndMortyUiState
        object Loading: RickAndMortyUiState
    }
    private var _rickAndMortyUiState: MutableStateFlow<RickAndMortyUiState> = MutableStateFlow(
        RickAndMortyUiState.Loading
    )
    val rickAndMortyUiState: StateFlow<RickAndMortyUiState> = _rickAndMortyUiState.asStateFlow()

    private var _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()

    private var _characterScreenUiState: MutableStateFlow<CharacterScreenUiState> = MutableStateFlow(
        CharacterScreenUiState()
    )
    val characterScreenUiState: StateFlow<CharacterScreenUiState> = _characterScreenUiState.asStateFlow()

    init{
        getCharacters()
    }

    private fun getCharacters(){
        viewModelScope.launch {
            try {
                _rickAndMortyUiState.value = RickAndMortyUiState.Success(
                    RickAndMortyApi.retrofitService.getCharacters()
                )
            }catch (e: IOException){
                _rickAndMortyUiState.value = RickAndMortyUiState.Error
            }catch (e: HttpException){
                _rickAndMortyUiState.value = RickAndMortyUiState.Error
            }
        }
    }


    fun navigate(navController: NavController){
        if (mainScreenUiState.value.screenName == "homeScreen"){
            _mainScreenUiState.update { currentState ->
                currentState.copy(
                    screenName = "characterScreen"
                )
            }
            navController.navigate("characterScreen")
        }else{
            _mainScreenUiState.update { currentState ->
                currentState.copy(
                    screenName = "homeScreen"
                )
            }
            navController.navigate("homeScreen"){
                popUpTo("homeScreen"){
                    inclusive=true
                }
            }
        }
    }

    fun showCharacterData(character: Character, navController: NavController){
        _characterScreenUiState.update { currenteState ->
            currenteState.copy(
                name = character.name,
                status = character.status,
                species = character.species,
                gender = character.gender,
                image = character.image,
                origin = character.origin.name
            )
        }
        navigate(navController)
    }

    fun ReturnHomeScreen(navController: NavController){
        _characterScreenUiState.update { currenteState ->
            currenteState.copy(
                name = "",
                status = "",
                species = "",
                gender = "",
                image = ""
            )
        }
        navigate(navController)
    }
}
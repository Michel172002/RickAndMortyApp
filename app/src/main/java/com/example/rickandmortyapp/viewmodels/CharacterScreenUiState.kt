package com.example.rickandmortyapp.viewmodels

import com.example.rickandmortyapp.data.Character
import com.example.rickandmortyapp.data.Origin

data class CharacterScreenUiState(
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val image: String = "",
    val origin: String = ""
)


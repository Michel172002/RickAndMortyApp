package com.example.rickandmortyapp.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.viewmodels.RickAndMortyViewModel

@Composable
fun CharacterScreen(
    navController: NavController,
    rickAndMortyViewModel: RickAndMortyViewModel
){
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0F) }
    val height = remember { mutableStateOf(0F) }
    val uiState by rickAndMortyViewModel.characterScreenUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(15.dp)
            .padding(top = 115.dp)
    ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = uiState.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RectangleShape)
                    .width(402.dp)
                    .height(412.dp)
                    .padding(bottom = 50.dp)
            )
        Text(text = "NAME: ${uiState.name}",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            ))
        Text(text = "STATUS: ${uiState.status}",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            ))
        Text(text = "SPECIES: ${uiState.species}",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            ))
        Text(text = "GENDER: ${uiState.gender}",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            ))
        Text(text = "ORIGIN: ${uiState.origin}",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            ))
    }
}
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.Character
import com.example.rickandmortyapp.data.CharactersList
import com.example.rickandmortyapp.viewmodels.RickAndMortyViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    rickAndMortyViewModel: RickAndMortyViewModel
){
    val uiState by rickAndMortyViewModel.rickAndMortyUiState.collectAsState()
    when(uiState){
        is RickAndMortyViewModel.RickAndMortyUiState.Loading -> LoadingScreen()
        is RickAndMortyViewModel.RickAndMortyUiState.Error -> ErrorScreen()
        is RickAndMortyViewModel.RickAndMortyUiState.Success -> CharacterList(
            (uiState as RickAndMortyViewModel.RickAndMortyUiState.Success).characters
        ){
            rickAndMortyViewModel.showCharacterData(it, navController)
        }
    }
}

@Composable
fun ErrorScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.loading_failed))
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = stringResource(
                id = R.string.loading
            )
        )
    }
}

@Composable
fun CharacterList(
    characters: CharactersList,
    onClickCard: (Character) -> Unit
){
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(top = 105.dp, bottom = 55.dp),
        columns = GridCells.Adaptive(198.dp)
    ){
        items(characters.results){character ->
            CharacterEntry(character = character) { onClickCard(character) }
        }
    }
}

@Composable
fun CharacterEntry(
    character: Character,
    onClickCard: () -> Unit
){
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0F) }
    val height = remember { mutableStateOf(0F) }

    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(197.dp)
            .height(197.dp)
            .clickable { onClickCard() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    }
            )
            Box(
                modifier = Modifier
                    .size(
                        width = width.value.dp,
                        height = height.value.dp,
                    )
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            100F,
                            500F,
                        )
                    )
            ) {

            }
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = character.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

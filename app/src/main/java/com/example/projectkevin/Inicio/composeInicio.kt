package com.example.projectkevin.Inicio

import android.R
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.PermissionChecker
import coil.compose.rememberAsyncImagePainter
import com.example.projectkevin.CharacterInf.CharacterInf
import com.example.projectkevin.MainActivity
import com.example.projectkevin.api.dataCharacter.CharacterList
import kotlinx.coroutines.delay

@Composable
fun BarSearch(checkCameraPermission: () -> Unit) {
    var date by remember { mutableStateOf("") }
    Box() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                Image(
                    painter = painterResource(
                        id = R.drawable.ic_menu_search
                    ),
                    contentDescription = "perfil",
                    modifier = Modifier.size(30.dp)
                )
            }
            TextField(
                value = date,
                onValueChange = { date = it },
                label = { Text(text = "Buscar") },
                modifier = Modifier
                    .background(color = Color.White)
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                Image(painter = painterResource(
                    id = com.example.projectkevin.R.drawable.`as`
                ),
                    contentDescription = "perfil",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { checkCameraPermission() }
                )
            }
        }
    }
}

@Composable
fun BarMenu() {
    val context = LocalContext.current
    val onBack: () -> Unit = {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Box(modifier = Modifier
            .align(Alignment.CenterVertically)
            .clickable { onBack() }
        ) {
            Image(
                painter = painterResource(id = com.example.projectkevin.R.drawable.back),
                contentDescription = "atras",
                modifier = Modifier.size(35.dp)
            )
        }

        Box() {
            Image(
                painter = painterResource(id = com.example.projectkevin.R.drawable.title),
                contentDescription = "titulo",
                modifier = Modifier.size(width = 280.dp, height = 50.dp)
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            Image(painter = painterResource(
                id = com.example.projectkevin.R.drawable.perf
            ),
                contentDescription = "perfil",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { }
            )
        }
    }
}

@Composable
fun Listcharactern(
    characters: List<CharacterList>,
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    checkCameraPermission: () -> Unit

) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val Items = characters.size
    val onCharacterInf: (String) -> Unit = { characterId ->
        val intent = Intent(context, CharacterInf::class.java)
        intent.putExtra("characterId", characterId)
        context.startActivity(intent)
        (context as? Activity)
    }
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(currentPage) {
        listState.scrollToItem(0)
    }
    LaunchedEffect(characters) {
        delay(1500)
        isLoading = false
    }
    Column(Modifier.fillMaxSize()) {
        BarMenu()
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )
        BarSearch(checkCameraPermission)
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    items(Items) { index ->
                        val character = characters[index]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCharacterInf(character.id.toString()) }
                                .padding(5.dp)
                                .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(android.graphics.Color.parseColor("#E4DFE4")),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            )
                            {
                                Image(
                                    painter = rememberAsyncImagePainter(character.image),
                                    contentDescription = "character",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(10.dp)
                                        .clip(CircleShape)
                                )
                                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                    Text(text = "Id: ${character.id}")
                                    Text(text = "Nombre: ${character.name}")
                                }
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                                    .size(width = 150.dp, height = 30.dp)
                                    .clickable {
                                        onPageChange(currentPage - 1)
                                    }
                            ) {
                                Text(
                                    text = "Anterior página",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                                    .size(width = 150.dp, height = 30.dp)
                                    .clickable {
                                        onPageChange(currentPage + 1)
                                    }
                            ) {
                                Text(
                                    text = "Siguiente página",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

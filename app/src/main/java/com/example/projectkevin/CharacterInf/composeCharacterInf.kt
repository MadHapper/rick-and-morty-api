package com.example.projectkevin.CharacterInf

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.projectkevin.R

@Composable
fun BarMenuInf() {
    val context = LocalContext.current
    val onBack: () -> Unit = {
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
                painter = painterResource(id = R.drawable.back),
                contentDescription = "atras",
                modifier = Modifier.size(35.dp)
            )
        }

        Box() {
            Image(
                painter = painterResource(id = R.drawable.title),
                contentDescription = "titulo",
                modifier = Modifier.size(width = 280.dp, height = 50.dp)
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            Image(painter = painterResource(
                id = R.drawable.perf
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
fun ImagenCharacterInf(characterId: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
        ,contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .background(color = Color.Black)
            .padding(2.dp)
        ){
            Image(
                painter = rememberAsyncImagePainter("https://rickandmortyapi.com/api/character/avatar/$characterId.jpeg"),
                contentDescription = "character",
                modifier = Modifier
                    .size(150.dp)
            )
        }
    }
}

@Composable
fun backgroundCharacterInf(viewModel: CharacterInfViewModel, characterId: String) {
    val characterDetails = viewModel.characterDetails
    viewModel.updateCurrentId(characterId.toInt())
    ConstraintLayout {
        val (boxTop, boxDown, boxEnd, menu, imgCharacter) = createRefs()

        Box(modifier = Modifier.constrainAs(menu) {
            top.linkTo(parent.top)
        }) {
            BarMenuInf()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Green)
                .constrainAs(boxTop) {
                    top.linkTo(menu.bottom)
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.White)
                .constrainAs(boxDown) {
                    top.linkTo(boxTop.bottom)
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .constrainAs(boxEnd) {
                    top.linkTo(boxDown.bottom)
                }
        )
        {
            characterDetails?.onSuccess { character ->
                Column() {
                    Text(text = "Id: ${character.id}")
                    Text(text = "Nombre: ${character.name}")
                    Text(text = "Estatus: ${character.status}")
                    Text(text = "Especie: ${character.species}")
                    Text(text = "Genero: ${character.gender}")
                    Text(text = "Origien: ${character.origin.name}")
                    Text(text = "Localización: ${character.location.name}")
                }
            }
        }
        Box(modifier = Modifier.constrainAs(imgCharacter) {
            top.linkTo(menu.bottom)
            bottom.linkTo(boxEnd.top)
        }) {
            ImagenCharacterInf(characterId)
        }
    }
}

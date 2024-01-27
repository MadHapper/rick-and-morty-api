package com.example.projectkevin.CharacterInf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.projectkevin.ui.theme.ProjectkevinTheme
import dagger.hilt.android.AndroidEntryPoint


class CharacterInf : ComponentActivity() {
    val viewModelId by viewModels<CharacterInfViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val characterId = intent.getStringExtra("characterId") ?: ""

        setContent {
            ProjectkevinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        backgroundCharacterInf(viewModelId,characterId)
                    }
                }
            }
        }
    }
}


package com.example.projectkevin.Inicio

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.projectkevin.CharacterInf.CharacterInf
import com.example.projectkevin.MainActivity
import com.example.projectkevin.R
import com.example.projectkevin.api.dataCharacter.CharacterList
import com.example.projectkevin.ui.theme.ProjectkevinTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import okhttp3.internal.wait


class Inicio : ComponentActivity() {
    private val viewModel by viewModels<viewModelInicio>()
    private val CAMERA_REQUEST_CODE = 0
    var currentPage by mutableStateOf(1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectkevinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Listcharactern(
                        viewModel.characters,
                        currentPage,
                        { newPage -> currentPage = newPage },
                        this::checkCameraPermission,
                    )
                    viewModel.loadCharacters(currentPage)

                }
            }
        }

    }


    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestCameraPermission()
        } else {
            //Toast.makeText(this, "lo has aceptado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }

    fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            Toast.makeText(this, "Permission rechargers", Toast.LENGTH_SHORT).show()
        } else {
            //El usuario nunca ha aceptado ni rechazado, así que le pedimos que acepte el permiso.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }
    }
}

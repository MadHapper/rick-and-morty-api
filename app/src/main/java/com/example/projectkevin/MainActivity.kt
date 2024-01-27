package com.example.projectkevin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.projectkevin.Inicio.Inicio
import com.example.projectkevin.ui.theme.ProjectkevinTheme



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectkevinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    VideoBackground()
                }
            }
        }
    }
}


@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoBackground() {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    exoPlayer.playWhenReady = true
    exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.fondo)
    val mediaItem = remember {
        MediaItem.Builder()
            .setUri(uri)
            .build()
    }
    exoPlayer.setMediaItem(mediaItem)
    exoPlayer.prepare()
    val onVideoClick: () -> Unit = {
        exoPlayer.playWhenReady = !exoPlayer.playWhenReady
        val intent = Intent(context, Inicio::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(factory = {
            PlayerView(it).apply {
                useController = false
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                setOnClickListener { onVideoClick() }
            }
        })
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BlinkingText()
        }
    }
}


@Composable
fun BlinkingText() {
    val fadeInOutAnimation = rememberInfiniteTransition()
    val alpha by fadeInOutAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Clip aquí",
            fontSize = MaterialTheme.typography.h3.fontSize,
            color = Color.White,
            modifier = Modifier.alpha(alpha)
        )
    }
}
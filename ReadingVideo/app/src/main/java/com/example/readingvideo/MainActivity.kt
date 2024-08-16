package com.example.readingvideo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : ComponentActivity() {
    private lateinit var exoPlayer: SimpleExoPlayer

    override fun onStart() {
        super.onStart()
        exoPlayer.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ExoPlayer
        exoPlayer = SimpleExoPlayer.Builder(this).build()

        // Prepare media source
        val uri = Uri.parse("YOUR_VIDEO_URI_HERE")
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)

        // Set content view with Jetpack Compose
        setContent {
            VideoPlayer(exoPlayer = exoPlayer)
        }
    }
}

@Composable
fun VideoPlayer(exoPlayer: SimpleExoPlayer) {
    // Create PlayerView composable to display video
    PlayerView(
        modifier = Modifier.fillMaxSize(),
        player = exoPlayer
    )
}
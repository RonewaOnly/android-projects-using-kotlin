package com.example.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.animations.ui.theme.AnimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Methods()
        }
    }
}

@Composable
fun Methods(){
    var visible by remember { mutableStateOf(false) }


    Button(
        onClick = { visible = true },
    ) {
        Text(text = "press")
    }
    AnimatedVisibility(
        visible,
        enter = expandIn(
            // Overwrites the default spring animation with tween
            animationSpec = tween(100, easing = LinearOutSlowInEasing),
            // Overwrites the corner of the content that is first revealed
            expandFrom = Alignment.BottomEnd
        ) {
            // Overwrites the initial size to 50 pixels by 50 pixels
            IntSize(50, 50)
        },
        exit = shrinkOut(
            tween(100, easing = FastOutSlowInEasing),
            // Overwrites the area of the content that the shrink animation will end on. The
            // following parameters will shrink the content's clip bounds from the full size of the
            // content to 1/10 of the width and 1/5 of the height. The shrinking clip bounds will
            // always be aligned to the CenterStart of the full-content bounds.
            shrinkTowards = Alignment.CenterStart
        ) { fullSize ->
            // Overwrites the target size of the shrinking animation.
            IntSize(fullSize.width / 10, fullSize.height / 5)
        }
    ) {
        // Content that needs to appear/disappear goes here:
        Text("Content to appear/disappear",
            Modifier
                .fillMaxWidth()
                .requiredHeight(200.dp)
        )
    }

}
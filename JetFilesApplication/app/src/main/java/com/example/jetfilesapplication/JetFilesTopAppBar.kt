package com.example.jetfilesapplication

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Generic TopAppBar used across the app
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetFilesTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    contentColor: Color = contentColorFor(Color.White),
    elevation: Dp = 10.dp
) {
    Surface(
        color = Color.White,
        elevation = elevation,
        modifier = modifier
    ) {
        navigationIcon?.let {
            TopAppBar(
                title = title,
                navigationIcon = it,
                actions = actions,
                backgroundColor = Color.Transparent,
                contentColor = contentColor,
                elevation = 0.dp
            )
        }
    }
}
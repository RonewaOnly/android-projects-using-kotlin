package com.example.jetfilesapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
* List item displaying individual [JetFile]
*
* @param file individual [JetFile]
* @param isFavorite indicates whether this file is starred
* */
@Composable
fun FileCard(
    file: JetFile,
    onSelectFile: (String) -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = {
                    onSelectFile(file.path)
                }
            )
    ) {
        PostImage(Modifier.padding(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        ) {
            PostTitle(file)
            Spacer(modifier = Modifier.height(3.dp))
            FileLastModified(file)
        }
        StarButton(
            isFavorite = isFavorite,
            onClick = onToggleFavorite,
            modifier = Modifier
                .padding(vertical = 2.dp, horizontal = 6.dp)
        )
    }
}

@Composable
fun PostImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_file),
        contentDescription = null, // decorative
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun PostTitle(file: JetFile) {
    Text(
        text = file.name,
        style = MaterialTheme.typography.subtitle1,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun FileLastModified(
    file: JetFile,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            text = file.modifierAt.formattedDate(),
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}

/**
 * Full-width divider with padding
 */
@Composable
fun FileListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}
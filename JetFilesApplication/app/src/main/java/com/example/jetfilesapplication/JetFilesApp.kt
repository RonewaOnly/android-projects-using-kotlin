package com.example.jetfilesapplication

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.jetfilesapplication.ui.theme.JetFilesApplicationTheme

@Composable
fun JetFilesApp(
    appContainer: AppContainer
){
    JetFilesApplicationTheme {
        ProvideWindowsInsets{
            //Drawing content behind the system windows. Jst a UX thing!
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
            }

            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(appContainer.fileRepository)
            )

            val context = LocalContext.current

            Scaffold {padding ->
                HomeScreen(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    homeViewModel = homeViewModel,
                    onSelectFile = { onSelectFile(context, it) }
                )

            }
        }
    }
}

fun onSelectFile(context: Context, path: String){
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", File(path))
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = uri
    context.startActivity(intent)
}
package com.example.superfitcompose.ui.image

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superfitcompose.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ImageScreen(
    navController: NavController,
    photoDate: String?,
    photoId: String?,
    viewModel: ImageViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.processIntent(ImageIntent.LoadImage(photoId ?: "", photoDate ?: ""))
    }

    val viewState by viewModel.getViewState().observeAsState(ImageViewState())

    if (viewState.navigateBack) {
        viewModel.processIntent(ImageIntent.NavigationProcessed)
        navController.navigateUp()
    }


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary) {
        Scaffold(
            modifier = Modifier
                .padding(),
            containerColor = MaterialTheme.colorScheme.secondary,
            topBar = {
                Box(modifier = Modifier.padding(start = 20.dp, top = 40.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.left),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { viewModel.processIntent(ImageIntent.ClickedOnNavigateBack) }
                    )
                }

            },
            bottomBar = {
                Box(
                    Modifier
                        .padding(start = 16.dp, bottom = 50.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        ),
                ) {
                    Text(
                        text = viewState.date ?: "---",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                    )
                }
            }
        ) { paddings ->

            ZoomableImage(
                bitmap = viewState.image
                    ?: ImageBitmap.imageResource(id = R.drawable.default_image),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddings)
            )


        }
    }
}

package com.example.superfitcompose.ui.trainprogress

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superfitcompose.R
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainProgressScreen(
    navController: NavController,
    viewModel: TrainProgressViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = true) {

    }

    val viewState by viewModel.getViewState().observeAsState(TrainProgressViewState())

    if (viewState.navigateBack) {
        viewModel.processIntent(TrainProgressIntent.NavigationProcessed)
        navController.navigateUp()
    }


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary) {
        Scaffold(
            modifier = Modifier
                .padding(),
            containerColor = MaterialTheme.colorScheme.secondary,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.left),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { viewModel.processIntent(TrainProgressIntent.ClickedOnNavigateBack) }

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = stringResource(id = R.string.train_progress),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }


            }
        ) {
            it
            Image(
                painter = painterResource(id = R.drawable.train_progress_bg),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds

            )
        }
    }
}

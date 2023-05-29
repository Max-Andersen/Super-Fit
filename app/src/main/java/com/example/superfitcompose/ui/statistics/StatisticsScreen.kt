package com.example.superfitcompose.ui.statistics

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.example.superfitcompose.ui.trainprogress.TrainProgressIntent
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticsViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = true) {

    }

    val viewState by viewModel.getViewState().observeAsState(StatisticsViewState())

    if (viewState.navigateBack) {
        viewModel.processIntent(StatisticsIntent.NavigationProcessed)
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
                            .clickable { viewModel.processIntent(StatisticsIntent.ClickedOnNavigateBack) }
                    )
                }
            }
        ) {
            it
            Image(
                painter = painterResource(id = R.drawable.statistic_bg),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

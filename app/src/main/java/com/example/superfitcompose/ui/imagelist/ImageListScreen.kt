package com.example.superfitcompose.ui.imagelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.example.superfitcompose.ui.shared.StringMap
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ImageListScreen(navController: NavController, viewModel: ImageListViewModel = koinViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.processIntent(ImageListIntent.LoadData)
    }

    val viewState by viewModel.getViewState().observeAsState(ImageListViewState())

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary) {
        Scaffold(
            modifier = Modifier
                .padding(start = 20.dp, top = 40.dp, end = 20.dp),
            containerColor = MaterialTheme.colorScheme.secondary,
            topBar = {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.left),
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
            }) { paddings ->

            Column(modifier = Modifier.padding(paddings)) {
                viewState.imageList?.forEach { photoCell ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        val monthName = stringResource(
                            id = StringMap.namesOfMonth[photoCell.key.substring(5, 7).toInt()]!!
                        )
                        Text(
                            text = stringResource(id = R.string.title_of_image_list).format(
                                monthName,
                                photoCell.key.substring(0, 4)
                            ),
                            style = MaterialTheme.typography.headlineSmall
                        )

                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            maxItemsInEachRow = 3
                        ) {
                            photoCell.value.forEach {
                                Image(
                                    bitmap = it.photo,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(110.dp)
                                        .padding(top = 16.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                    }
                }
            }

        }
    }


}

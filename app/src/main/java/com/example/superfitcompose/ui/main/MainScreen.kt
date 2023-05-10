package com.example.superfitcompose.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.R
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme
import com.example.superfitcompose.ui.theme.greyTint

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel = viewModel()) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.main_screen_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(
                        Color.White, shape = RoundedCornerShape(
                            topStart = 24.dp, topEnd = 24.dp
                        )
                    )
            )
        }

        MainScreenFilling()


    }
}


@Composable
fun MainScreenFilling() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        if (true) {  // Todo state of showing list of exercises
            Text(
                text = stringResource(id = R.string.my_body),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            MyBodCard()

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.last_exercises),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )

                Text(
                    text = stringResource(id = R.string.see_all),
                    style = MaterialTheme.typography.bodySmall,
                    color = greyTint,
                    modifier = Modifier.clickable {
                        // Todo navigation
                    }
                )

            }



            ExerciseCard(type = stringResource(id = R.string.push_ups))

            ExerciseCard(type = stringResource(id = R.string.plank))



            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(bottom = 34.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.Bottom),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.left),
                    colorFilter = ColorFilter.tint(color = Color.Black),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = R.string.sign_out),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
            }

        } else {
            Text(
                text = stringResource(id = R.string.exercises),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            ExerciseCard(type = stringResource(id = R.string.push_ups))
            ExerciseCard(type = stringResource(id = R.string.plank))
            ExerciseCard(type = stringResource(id = R.string.squats))
            ExerciseCard(type = stringResource(id = R.string.crunch))
            ExerciseCard(type = stringResource(id = R.string.running))
        }


    }
}

@Composable
fun MyBodCard() {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            var sizeInDp by remember { mutableStateOf(DpSize.Zero) }
            val density = LocalDensity.current

            Image(
                painter = painterResource(id = R.drawable.mask_group_my_body),
                contentDescription = null,
                modifier = Modifier.onSizeChanged {
                    sizeInDp = density.run {
                        DpSize(
                            it.width.toDp(),
                            it.height.toDp()
                        )
                    }
                }
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = sizeInDp.height)
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.weight),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "76 kg",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.height),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "178 cm",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))


                Row(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.Bottom),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.details),
                        style = MaterialTheme.typography.bodySmall,
)
                    Image(
                        painter = painterResource(id = R.drawable.little_right),
                        contentDescription = null
                    )

                }


            }
        }

    }
}

@Composable
fun ExerciseCard(type: String) {
    val data = when (type) {
        stringResource(id = R.string.push_ups) -> Pair(
            stringResource(id = R.string.push_ups_description), painterResource(
                id = R.drawable.mask_group_push_ups
            )
        )

        stringResource(id = R.string.plank) -> Pair(
            stringResource(id = R.string.plank_description), painterResource(
                id = R.drawable.mask_group_plank
            )
        )

        stringResource(id = R.string.squats) -> Pair(
            stringResource(id = R.string.squats_description), painterResource(
                id = R.drawable.mask_group_squats
            )
        )

        stringResource(id = R.string.crunch) -> Pair(
            stringResource(id = R.string.crunch_description), painterResource(
                id = R.drawable.mask_group_crunch
            )
        )

        else -> Pair(
            stringResource(id = R.string.running_description), painterResource(
                id = R.drawable.mask_group_running
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(painter = data.second, contentDescription = null)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = data.first, style = MaterialTheme.typography.bodySmall)
            }
        }

    }

}

@Preview
@Composable
fun MainScreenPreview() {
    SuperFitComposeTheme {
        MainScreen(navController = rememberNavController())
    }
}
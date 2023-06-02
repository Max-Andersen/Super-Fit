package com.example.superfitcompose.ui.trainprogress

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.example.superfitcompose.data.network.models.TrainingType
import org.koin.androidx.compose.koinViewModel

internal const val baseHeight = 640f   // value to match layout from figma
internal const val baseWidth = 360f

internal val pushUpsPoint = Offset(137f, 155f)
internal val plankPoint = Offset(129f, 224f)
internal val crunchPoint = Offset(99f, 268f)
internal val squatsPoint = Offset(127f, 421f)
internal val runningPoint = Offset(127f, 593f)

internal val pushUpsLine = listOf(
    Pair(Offset(139.5f, 153f), Offset(167f, 126f)),
    Pair(Offset(166.5f, 126f), Offset(294.5f, 126f))
)

internal val plankLine = listOf(Pair(Offset(132.5f, 224f), Offset(302f, 224f)))

internal val crunchLine = listOf(
    Pair(Offset(101f, 271f), Offset(153.5f, 324f)),
    Pair(Offset(153f, 323.5f), Offset(295.5f, 323.5f))
)

internal val squatsLine = listOf(
    Pair(Offset(129.5f, 424f), Offset(159f, 454f)),
    Pair(Offset(158.5f, 453.5f), Offset(292f, 453.5f))
)

internal val runningLine = listOf(
    Pair(Offset(130f, 592f), Offset(159f, 563f)),
    Pair(Offset(158.5f, 563.5f), Offset(292f, 563.5f))
)

internal const val pushUpsX = 175f
internal val pushUpsY = listOf(107f, 128f, 143f)

internal const val plankX = 161f
internal val plankY = listOf(205f, 228f, 243f)

internal const val crunchX = 163f
internal val crunchY = listOf(303f, 326f, 341f)

internal const val squatsX = 167f
internal val squatsY = listOf(433f, 456f, 471f)

internal const val runningX = 167.5f
internal val runningY = listOf(542f, 565f, 580f)


@Composable
fun TrainProgressScreen(
    navController: NavController,
    viewModel: TrainProgressViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.processIntent(TrainProgressIntent.LoadData)
    }

    val viewState by viewModel.getViewState().observeAsState(TrainProgressViewState())

    if (viewState.navigateBack) {
        viewModel.processIntent(TrainProgressIntent.NavigationProcessed)
        navController.navigateUp()
    }

    var imageSize by remember { mutableStateOf(IntSize.Zero) }
    //var positionInRootTopBar by remember { mutableStateOf(Offset.Zero) }

    val surfaceColor = MaterialTheme.colorScheme.surface

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary) {
        Image(
            painter = painterResource(id = R.drawable.train_progress_bg),
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    imageSize = coordinates.size
                    //positionInRootTopBar = coordinates.positionInRoot()
                },
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        TrainProgressTitle(viewModel::processIntent)

        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawLines(this, surfaceColor, imageSize)
            }) {

            val density = LocalDensity.current

            PlaceOnDrawTexts(
                density,
                imageSize,
                viewState.pushUpsTrainProgress,
                viewState.plankTrainProgress,
                viewState.crunchTrainProgress,
                viewState.squatsTrainProgress,
                viewState.runningTrainProgress
            )
        }
    }
}

@Composable
fun PlaceOnDrawTexts(
    density: Density,
    imageSize: IntSize,
    pushUpsTrainProgress: TrainProgress?,
    plankTrainProgress: TrainProgress?,
    crunchTrainProgress: TrainProgress?,
    squatsTrainProgress: TrainProgress?,
    runningTrainProgress: TrainProgress?
) {
    pushUpsTrainProgress?.let {
        OnDrawText(
            TrainingType.PUSH_UP, density, imageSize,
            it
        )
    }
    plankTrainProgress?.let {
        OnDrawText(
            TrainingType.PLANK, density, imageSize,
            it
        )
    }
    crunchTrainProgress?.let {
        OnDrawText(
            TrainingType.CRUNCH, density, imageSize,
            it
        )
    }
    squatsTrainProgress?.let {
        OnDrawText(
            TrainingType.SQUATS, density, imageSize,
            it
        )
    }
    runningTrainProgress?.let {
        OnDrawText(
            TrainingType.RUNNING, density, imageSize,
            it
        )
    }
}

fun drawLines(drawScope: DrawScope, surfaceColor: Color, imageSize: IntSize) {
    drawScope.apply {
        drawCircle(
            surfaceColor,
            4.dp.toPx(),
            center = calculateOffset(imageSize, pushUpsPoint),
            style = Stroke(width = 2.dp.toPx())
        )
        pushUpsLine.forEach {
            drawLine(
                surfaceColor,
                calculateOffset(imageSize, it.first),
                calculateOffset(imageSize, it.second),
                strokeWidth = 2.dp.toPx()
            )
        }


        drawCircle(
            surfaceColor,
            4.dp.toPx(),
            center = calculateOffset(imageSize, plankPoint),
            style = Stroke(width = 2.dp.toPx())
        )
        plankLine.forEach {
            drawLine(
                surfaceColor,
                calculateOffset(imageSize, it.first),
                calculateOffset(imageSize, it.second),
                strokeWidth = 2.dp.toPx()
            )
        }


        drawCircle(
            surfaceColor,
            4.dp.toPx(),
            center = calculateOffset(imageSize, crunchPoint),
            style = Stroke(width = 2.dp.toPx())
        )
        crunchLine.forEach {
            drawLine(
                surfaceColor,
                calculateOffset(imageSize, it.first),
                calculateOffset(imageSize, it.second),
                strokeWidth = 2.dp.toPx()
            )
        }

        drawCircle(
            surfaceColor,
            4.dp.toPx(),
            center = calculateOffset(imageSize, squatsPoint),
            style = Stroke(width = 2.dp.toPx())
        )
        squatsLine.forEach {
            drawLine(
                surfaceColor,
                calculateOffset(imageSize, it.first),
                calculateOffset(imageSize, it.second),
                strokeWidth = 2.dp.toPx()
            )
        }

        drawCircle(
            surfaceColor,
            4.dp.toPx(),
            center = calculateOffset(imageSize, runningPoint),
            style = Stroke(width = 2.dp.toPx())
        )
        runningLine.forEach {
            drawLine(
                surfaceColor,
                calculateOffset(imageSize, it.first),
                calculateOffset(imageSize, it.second),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

@Composable
fun TrainProgressTitle(sendIntent: (TrainProgressIntent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
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
                    .clickable { sendIntent(TrainProgressIntent.ClickedOnNavigateBack) }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.train_progress),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun OnDrawText(
    type: TrainingType,
    density: Density,
    imageSize: IntSize,
    trainProgress: TrainProgress,
) {
    var xPadding = 0f
    var yPaddings = listOf<Float>()
    var titleText = ""
    var lastTrainText = ""

    when (type) {
        TrainingType.PUSH_UP -> {
            xPadding = pushUpsX
            yPaddings = pushUpsY
            titleText = stringResource(id = R.string.push_ups)
            lastTrainText = stringResource(id = R.string.last_train_times)
        }

        TrainingType.PLANK -> {
            xPadding = plankX
            yPaddings = plankY
            titleText = stringResource(id = R.string.plank)
            lastTrainText = stringResource(R.string.last_train_seconds)
        }

        TrainingType.CRUNCH -> {
            xPadding = crunchX
            yPaddings = crunchY
            titleText = stringResource(id = R.string.crunch)
            lastTrainText = stringResource(R.string.last_train_times)
        }

        TrainingType.SQUATS -> {
            xPadding = squatsX
            yPaddings = squatsY
            titleText = stringResource(id = R.string.squats)
            lastTrainText = stringResource(R.string.last_train_times)
        }

        TrainingType.RUNNING -> {
            xPadding = runningX
            yPaddings = runningY
            titleText = stringResource(id = R.string.running)
            lastTrainText = stringResource(R.string.last_train_meters)
        }
    }

    val titlePadding = calculateOffset(imageSize, Offset(xPadding, yPaddings[0]))
    val lastTrainPadding = calculateOffset(imageSize, Offset(xPadding, yPaddings[1]))
    val lastProgressPadding =
        calculateOffset(imageSize, Offset(xPadding, yPaddings[2]))

    Text(
        text = titleText,
        style = MaterialTheme.typography.headlineSmall,
        fontSize = 14.sp,
        modifier = Modifier.offset(
            x = with(density) {
                titlePadding.x.toDp()
            },
            y = with(density) {
                titlePadding.y.toDp()
            }

        )
    )

    Row(modifier = Modifier.offset(
        x = with(density) {
            lastTrainPadding.x.toDp()
        },
        y = with(density) {
            lastTrainPadding.y.toDp()
        }
    )) {
        Text(
            text = stringResource(id = R.string.last_train),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Text(
            text = lastTrainText.format(trainProgress.lastTrain.toString()),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 12.sp
        )
    }

    Row(
        modifier = Modifier.offset(
            x = with(density) {
                lastProgressPadding.x.toDp()
            },
            y = with(density) {
                lastProgressPadding.y.toDp()
            },
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.progress),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Text(
            text = stringResource(id = R.string.progress_percents).format(trainProgress.progress.toString()),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.size(4.dp))
        Image(
            painter = painterResource(
                id = if (trainProgress.progress >= 0) {
                    R.drawable.up
                } else {
                    R.drawable.down
                }
            ), contentDescription = null
        )
    }
}

fun calculateOffset(sizeOfImage: IntSize, figmaOffset: Offset): Offset {
    return Offset(
        (figmaOffset.x * sizeOfImage.width.toFloat()) / baseWidth,
        (figmaOffset.y * sizeOfImage.height.toFloat()) / baseHeight
    )
}

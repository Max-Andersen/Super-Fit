package com.example.superfitcompose.ui.mybody

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.example.superfitcompose.ui.Routes
import com.example.superfitcompose.ui.shared.DateMapper
import com.example.superfitcompose.ui.shared.models.PhotoData
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream


@Composable
fun MyBodyScreen(navController: NavController, viewModel: MyBodyViewModel = koinViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.processIntent(MyBodyIntent.LoadData)
    }

    val viewState by viewModel.getViewState().observeAsState(MyBodyViewState())

    BackHandler(true) {
        navController.navigate(Routes.MAIN_SCREEN) {
            popUpTo(
                navController.graph.id
            )
        }
    }

    if (viewState.editHeight) {
        EnterBodyParam(type = BodyParamsTypes.Height,
            text = viewState.inputHeight?.toString() ?: "",
            { viewModel.processIntent(MyBodyIntent.CloseEnterHeight) },
            { viewModel.processIntent(MyBodyIntent.SaveBodyParams(BodyParamsTypes.Height)) }) { newText ->
            if (!newText.contains('0')) {
                viewModel.processIntent(MyBodyIntent.EnterHeight(newText.toInt()))
            }
        }
    }

    if (viewState.editWeight) {
        EnterBodyParam(type = BodyParamsTypes.Weight,
            text = viewState.inputWeight?.toString() ?: "",
            { viewModel.processIntent(MyBodyIntent.CloseEnterWeight) },
            { viewModel.processIntent(MyBodyIntent.SaveBodyParams(BodyParamsTypes.Weight)) }) { newText ->
            if (!newText.contains('0')) {
                viewModel.processIntent(MyBodyIntent.EnterWeight(newText.toInt()))
            }
        }
    }

    if (viewState.addImage) {
        AddImage(viewModel::processIntent, viewState.imageUri)
    }

    if (viewState.seeTrainProgress) {
        viewModel.processIntent(MyBodyIntent.NavigationProcessed)
        navController.navigate(Routes.TRAIN_PROGRESS)
    }

    if (viewState.seeMyProgress) {
        viewModel.processIntent(MyBodyIntent.NavigationProcessed)
        navController.navigate(Routes.IMAGE_LIST)
    }

    if (viewState.seeStatistics) {
        viewModel.processIntent(MyBodyIntent.NavigationProcessed)
        navController.navigate(Routes.STATISTICS)
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            MyBodyInformation(viewState.weight, viewState.height, viewModel::processIntent)
            MyProgress(viewModel::processIntent, viewState.firstPhoto, viewState.latestPhoto)
            AnotherSectionsNavigation(viewModel::processIntent)
        }
    }
}

@Composable
fun MyBodyInformation(weight: Int, height: Int, sendIntent: (MyBodyIntent) -> Unit) {
    Text(
        text = stringResource(id = R.string.my_body),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 48.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MyWeight(weight) { sendIntent(MyBodyIntent.ClickedOnUpdateWeight) }
        MyHeight(height) { sendIntent(MyBodyIntent.ClickedOnUpdateHeight) }
    }
}

@Composable
fun MyHeight(height: Int, sendIntent: () -> Unit) {
    Column(
        modifier = Modifier.wrapContentHeight(Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.height).format(height.toString()),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = stringResource(id = R.string.edit),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { sendIntent() })
    }
}

@Composable
fun MyWeight(weight: Int, sendIntent: () -> Unit) {
    Column(
        modifier = Modifier.wrapContentHeight(Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = stringResource(id = R.string.weight).format(weight.toString()),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = stringResource(id = R.string.edit),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { sendIntent() })
    }
}

@Composable
fun MyProgress(
    sendIntent: (MyBodyIntent) -> Unit, firstPhoto: PhotoData?, latestPhoto: PhotoData?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.my_progress),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = stringResource(id = R.string.see_all),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { sendIntent(MyBodyIntent.ClickedOnSeeAllProgress) })
    }

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(), Alignment.BottomStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
                .clip(
                    RoundedCornerShape(
                        8.dp
                    )
                )
                .background(Color.White)
        ) {

            Image(
                bitmap = firstPhoto?.photo
                    ?: ImageBitmap.imageResource(id = R.drawable.default_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.width(4.dp))
            Image(
                bitmap = latestPhoto?.photo
                    ?: ImageBitmap.imageResource(id = R.drawable.default_image),
                contentDescription = null,
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth(1f),
                contentScale = ContentScale.Crop
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                Modifier
                    .padding()
                    .background(
                        MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp)
                    ),
            ) {
                Text(text = firstPhoto?.date?.let { date ->
                    DateMapper(date)()
                } ?: "---",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.padding(
                        start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp
                    ))
            }
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.End, Alignment.Bottom) {
                Box(
                    Modifier.background(
                            MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp)
                        ),
                ) {
                    Text(text = latestPhoto?.date?.let { date ->
                        date.split("-").let {
                            "${it[2]}.${it[1]}.${it[0]}"
                        }
                    } ?: "---",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(
                            start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp
                        ))
                }
                Spacer(modifier = Modifier.size(36.dp))
                Image(painter = painterResource(id = R.drawable.add_image),
                    contentDescription = null,
                    modifier = Modifier.clickable { sendIntent(MyBodyIntent.ClickedOnAddImage) })
            }
        }
    }
}

@Composable
fun AnotherSectionsNavigation(sendIntent: (MyBodyIntent) -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 37.dp)
            .clickable { sendIntent(MyBodyIntent.ClickedOnTrainProgress) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.train_progress),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))
        Image(painter = painterResource(id = R.drawable.right), contentDescription = null)
    }
    Row(
        modifier = Modifier
            .padding(top = 22.dp)
            .clickable { sendIntent(MyBodyIntent.ClickedOnStatistics) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.statistics),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))
        Image(painter = painterResource(id = R.drawable.right), contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterBodyParam(
    type: BodyParamsTypes,
    text: String,
    close: () -> Unit,
    save: () -> Unit,
    updateValue: (String) -> Unit
) {
    Dialog(
        onDismissRequest = {},
    ) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.error
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 15.dp,
                            top = 15.dp
                        )
                ) {

                    Text(
                        text = stringResource(id = R.string.change_body_param).format(type),
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 20.sp
                    )

                    TextField(value = text, onValueChange = { newText: String ->
                        updateValue(newText)
                    }, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ), label = {
                        Text(
                            text = type.name,
                            color = MaterialTheme.colorScheme.onError,
                            fontSize = 12.sp
                        )
                    }, placeholder = {
                        Text(
                            text = stringResource(id = R.string.new_value),
                            color = MaterialTheme.colorScheme.errorContainer,
                            fontSize = 16.sp
                        )
                    }, colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White, containerColor = Color.Transparent
                    )
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = stringResource(id = R.string.cancel),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.clickable { close() })
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(text = stringResource(id = R.string.change),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.clickable { save() })
                    }
                }
            }
        }
    }
}

@Composable
fun AddImage(sendIntent: (MyBodyIntent) -> Unit, imageUri: Uri?) {

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        sendIntent(MyBodyIntent.NewImageSelected(uri))
    }

    val context = LocalContext.current

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            it?.let {
                val bytes = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.PNG, 100, bytes)
                val path: String = MediaStore.Images.Media.insertImage(
                    context.contentResolver, it, "Title", null
                )
                sendIntent(MyBodyIntent.NewImageSelected(Uri.parse(path)))
            }
        }

    Dialog(onDismissRequest = { sendIntent(MyBodyIntent.ClosePhotoSelect) }) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.error
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Image(bitmap = imageUri?.let {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(LocalContext.current.contentResolver, it)
                    ).asImageBitmap()
                } ?: ImageBitmap.imageResource(id = R.drawable.default_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            cameraLauncher.launch()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(text = "Camera")
                    }
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(text = "Gallery")
                    }
                }
                Button(
                    onClick = { sendIntent(MyBodyIntent.SaveNewImage) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(text = "Save")
                }
            }

        }
    }
}

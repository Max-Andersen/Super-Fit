package com.example.superfitcompose.ui.mybody

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.superfitcompose.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyBodyScreen(navController: NavController, viewModel: MyBodyViewModel = koinViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.processIntent(MyBodyIntent.GetBodyParams)
        viewModel.processIntent(MyBodyIntent.GetLastImages)
    }

    val viewState by viewModel.getViewState().observeAsState(MyBodyViewState())


    if (viewState.editWeight) {
        EnterBodyParam(
            type = "weight",
            text = viewState.inputWeight?.toString() ?: ""
        ) { newText -> viewModel.processIntent(MyBodyIntent.EnterWeight(newText)) }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            MyBodyInformation(viewState.weight, viewState.height, viewModel::processIntent)
            MyProgress(viewModel::processIntent)
            AnotherSectionsNavigation(viewModel::processIntent)
        }
    }


}

@Composable
fun AnotherSectionsNavigation(sendIntent: (MyBodyIntent) -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 37.dp)
            .clickable { sendIntent(MyBodyIntent.ClickedOnSeeAllProgress) },
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

@Composable
fun MyProgress(sendIntent: (MyBodyIntent) -> Unit) {
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

    Box(  // Todo(придумать что-то на наложением дат на фото)
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_image),
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp, bottomStart = 8.dp
                        )
                    )
                    .fillMaxWidth(0.5f)
            )
            Image(
                painter = painterResource(id = R.drawable.default_image), contentDescription = null,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = 8.dp, bottomEnd = 8.dp
                        )
                    )
                    .fillMaxWidth(1f)
            )
        }


        Row(
            modifier = Modifier.wrapContentHeight(Alignment.Bottom),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {}) { }
            Button(onClick = {}) { }
        }
    }


}

@Composable
fun MyBodyInformation(weight: Int?, height: Int?, sendIntent: (MyBodyIntent) -> Unit) {
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
fun MyHeight(height: Int?, sendIntent: () -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentHeight(Alignment.Top)
            .wrapContentWidth()
    ) {
        Text(
            text = height?.toString() ?: stringResource(id = R.string.undefined_line),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(id = R.string.edit),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { sendIntent() })
    }
}

@Composable
fun MyWeight(weight: Int?, sendIntent: () -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentHeight(Alignment.Top)
    ) {
        Text(
            text = weight?.toString() ?: stringResource(id = R.string.undefined_line),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(id = R.string.edit),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { sendIntent() })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterBodyParam(type: String, text: String, sendIntent: (Int) -> Unit) {
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
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 15.dp, top = 15.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.change_body_param).format(type),
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 20.sp
                    )

                    TextField(
                        value = text,
                        onValueChange = { newText: String ->
                            sendIntent(newText.toInt())
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        label = {
                            Text(
                                text = type,
                                color = MaterialTheme.colorScheme.onError,
                                fontSize = 12.sp
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.new_value),
                                color = MaterialTheme.colorScheme.errorContainer,
                                fontSize = 16.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            containerColor = Color.Transparent
                        )
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onError
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(
                            text = stringResource(id = R.string.change),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onError
                        )
                    }

                }
            }
        }

    }
}

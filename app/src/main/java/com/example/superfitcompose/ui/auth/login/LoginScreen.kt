package com.example.superfitcompose.ui.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.superfitcompose.R
import com.example.superfitcompose.ui.Routes
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavHostController) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.auth_screen_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.super_fit),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(start = 37.dp, top = 44.dp, end = 37.dp)
                .wrapContentHeight(align = Alignment.Top)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        val viewState by viewModel.getScreenState().observeAsState(LoginViewState())

        if (viewState.errorMessage.isNotEmpty()) {
            Toast.makeText(LocalContext.current, viewState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.processIntent(LoginScreenIntent.ErrorProcessed)
        }

        if (viewState.navigateToEnterPassword) {
            viewModel.processIntent(LoginScreenIntent.NavigationProcessed)
            navController.navigate(Routes.ENTER_PASSWORD+ "/" + viewState.login)
        }

        if (viewState.navigateToRegister) {
            viewModel.processIntent(LoginScreenIntent.NavigationProcessed)
            navController.navigate(Routes.REGISTER)
        }

        EnterUsernameScreen(
            text = viewState.login,
            sendIntent = viewModel::processIntent
        )
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterUsernameScreen(text: String, sendIntent: (LoginScreenIntent) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        interactionSource = interactionSource,
        onValueChange = { newText -> sendIntent(LoginScreenIntent.UserNameInput(newText)) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(top = 337.dp, start = 59.dp, end = 59.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.White.copy(alpha = 0.48f),
                    disabledIndicatorColor = Color.White.copy(alpha = 0.48f),
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.48f)
                ),
                focusedIndicatorLineThickness = 2.dp,
                unfocusedIndicatorLineThickness = 2.dp
            ),
        cursorBrush = SolidColor(Color.White)
    ) { innerTextField ->
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = text,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.username),
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            contentPadding = PaddingValues(bottom = 6.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }


    Row(
        modifier = Modifier
            .padding(start = 62.dp, top = 377.dp)
            .wrapContentHeight(Alignment.Top)
            .wrapContentWidth(Alignment.Start)
            .clickable { sendIntent(LoginScreenIntent.EnterCodeButtonClicked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.sign_in),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))
        Image(painter = painterResource(id = R.drawable.right), contentDescription = null)
    }

    Row(
        modifier = Modifier
            .padding(bottom = 34.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.Bottom)
            .clickable { sendIntent(LoginScreenIntent.SignUpNavigationButtonClicked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))
        Image(painter = painterResource(id = R.drawable.right), contentDescription = null)
    }
}


@Preview
@Composable
fun LoginPreview() {
    SuperFitComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_screen_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )


        }
    }
}
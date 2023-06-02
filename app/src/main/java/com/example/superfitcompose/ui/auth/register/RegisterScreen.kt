package com.example.superfitcompose.ui.auth.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.example.superfitcompose.bottomPadding
import com.example.superfitcompose.ui.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = koinViewModel()) {

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


        val viewState by viewModel.getScreenState().observeAsState(RegisterViewState())

        if (viewState.errorMessage.isNotEmpty()) {
            Toast.makeText(LocalContext.current, viewState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.processIntent(RegisterScreenIntent.ErrorProcessed)
        }

        if (viewState.navigateMainScreen) {
            viewModel.processIntent(RegisterScreenIntent.NavigationProcessed)
            navController.navigate(Routes.MAIN_SCREEN) {
                popUpTo(
                    navController.graph.id
                )
            }
        }

        if (viewState.navigateToLogin) {
            viewModel.processIntent(RegisterScreenIntent.NavigationProcessed)
            navController.navigate(Routes.LOGIN)
        }


        Column(
            modifier = Modifier.padding(start = 52.dp, end = 52.dp, top = 248.dp),
            verticalArrangement = Arrangement.spacedBy(37.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PlaceRegisterTextFields(viewState.username, viewState.email, viewState.code, viewState.codeConfirmation, viewModel::processIntent)

            Row(
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.Bottom)
                    .clickable { viewModel.processIntent(RegisterScreenIntent.SignUpButtonClicked) },
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


        Row(
            modifier = Modifier
                .padding(bottom = 34.dp + bottomPadding)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.Bottom)
                .clickable { viewModel.processIntent(RegisterScreenIntent.SignInNavigationButtonClicked) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.left), contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun PlaceRegisterTextFields(
    username: String,
    email: String,
    code: String,
    codeConfirmation: String,
    sendIntent: (RegisterScreenIntent) -> Unit
) {
    RegisterTextField(
        username,
        R.string.username,
        KeyboardOptions(imeAction = ImeAction.Done)
    ) { newUserName ->
        sendIntent(
            RegisterScreenIntent.UserNameInput(newUserName)
        )
    }
    RegisterTextField(
        email,
        R.string.email,
        KeyboardOptions(imeAction = ImeAction.Done)
    ) { newEmail ->
        sendIntent(
            RegisterScreenIntent.EmailInput(newEmail)
        )
    }
    RegisterTextField(
        code,
        R.string.code,
        KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
    ) { newCode ->
        if (!newCode.contains('0')){
            sendIntent(
                RegisterScreenIntent.CodeInput(newCode)
            )
        }
    }
    RegisterTextField(
        codeConfirmation,
        R.string.repeat_code,
        KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
    ) { newCodeConfirmation ->
        if (!newCodeConfirmation.contains('0')){
            sendIntent(
                RegisterScreenIntent.CodeConfirmationInput(newCodeConfirmation)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTextField(
    text: String,
    hint: Int,
    keyboardOptions: KeyboardOptions,
    sendText: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        interactionSource = interactionSource,
        onValueChange = { newText -> sendText(newText) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .fillMaxWidth()
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
                    text = stringResource(id = hint),
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
}
package com.example.superfitcompose.ui.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.superfitcompose.R

@Composable
fun Controllers(
    isPaused: Boolean,
    isFinished: Boolean,
    onPause: () -> Unit,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        if (isPaused) {
            ControlButton(onClick = onStart, enabled = !isFinished, text = R.string.start_exercise)
            Spacer(modifier = Modifier.size(16.dp))
            ControlButton(onClick = onStop, text = R.string.finish_exercise)
        } else {
            ControlButton(onClick = onPause, text = R.string.pause_exercise)
        }
    }
}


@Composable
internal fun ControlButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .clickable {
                if (enabled) onClick()
            },
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(top = 14.dp, bottom = 14.dp)
        )
    }
}
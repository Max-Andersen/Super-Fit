package com.example.superfitcompose.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfitcompose.R
import com.example.superfitcompose.data.network.models.TrainingType
import java.util.Locale

@Composable
fun ExerciseCard(type: TrainingType, cardClick: () -> Unit) {
    val data = when (type) {
        TrainingType.PUSH_UP -> Pair(
            stringResource(id = R.string.push_ups_description), painterResource(
                id = R.drawable.mask_group_push_ups
            )
        )

        TrainingType.PLANK -> Pair(
            stringResource(id = R.string.plank_description), painterResource(
                id = R.drawable.mask_group_plank
            )
        )

        TrainingType.SQUATS -> Pair(
            stringResource(id = R.string.squats_description), painterResource(
                id = R.drawable.mask_group_squats
            )
        )

        TrainingType.CRUNCH -> Pair(
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
            .clickable { cardClick() }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(painter = data.second, contentDescription = null)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                Text(
                    text = type.name.replace('_', ' ').lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = data.first, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
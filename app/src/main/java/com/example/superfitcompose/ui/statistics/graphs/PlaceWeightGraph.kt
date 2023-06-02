package com.example.superfitcompose.ui.statistics.graphs

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.superfitcompose.domain.models.BodyParameters
import com.example.superfitcompose.ui.statistics.WeightGraph

@Composable
fun PlaceWeightGraph(weightHistory: List<BodyParameters>?) {
    if (weightHistory != null) {
        val yStep = 20
        val testPoints = listOf(120f, 125f, 100f, 50f, 80f, 25f, 130f, 90f, 120f, 60f)
        val testX = (0..9).map {
            val char = (it + 1).toString()
            "$char-$char-$char"
        }
        val testY = (0..6).map { (it + 1) * yStep }

        val xValues = weightHistory.flatMap { listOf(it.date) }
        val yValues = (0..6).map { (it + 1) * yStep }
        val points = weightHistory.flatMap { listOf(it.weight.toFloat()) }

        val paddingSpace = 20.dp

        WeightGraph(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(top = 25.dp, end = 25.dp)
                .height(240.dp)
                .width(points.size * 50.dp),
            xValues = xValues,
            yValues = yValues,
            points = points,
            paddingSpace = paddingSpace,
            verticalStep = yStep,
            gridColor = Color.White,
            lineColor = MaterialTheme.colorScheme.surface,
            pointColor = MaterialTheme.colorScheme.surface,
        )
    }
}
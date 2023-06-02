package com.example.superfitcompose.ui.statistics

import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun TrainGraph(
    modifier: Modifier,
    xValues: List<String>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int,
    gridColor: Color,
    lineColor: Color,
) {
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(
        modifier = modifier,
    ) {
        val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
        val yAxisSpace = size.height / yValues.size

        drawLine(
            color = gridColor,
            start = Offset(100f, size.height - 100),
            end = Offset(size.width, size.height - 100),
            strokeWidth = 4f
        )

        drawLine(
            color = gridColor,
            start = Offset(size.width, 0f),
            end = Offset(size.width, size.height - 95),
            strokeWidth = 4f
        )

        drawLine(
            color = gridColor,
            start = Offset(100f, 0f),
            end = Offset(100f, size.height - 95),
            strokeWidth = 4f
        )

        for (i in xValues.indices) {
            drawContext.canvas.nativeCanvas.drawText(
                xValues[i].split("-").let {
                    "${it[2]}.${it[1]}.${it[0]}"
                },
                xAxisSpace * (i + 1),
                size.height - 30,
                textPaint
            )
            drawLine(
                color = gridColor,
                start = Offset(xAxisSpace * (i + 1), size.height - 100),
                end = Offset(xAxisSpace * (i + 1), 0f),
                strokeWidth = 4f
            )
        }


        for (i in yValues.indices) {
            drawContext.canvas.nativeCanvas.drawText(
                "${yValues[i]}",
                paddingSpace.toPx() / 2f,
                size.height - yAxisSpace * (i + 1),
                textPaint
            )
            drawLine(
                color = gridColor,
                start = Offset(100f, size.height - yAxisSpace * (i + 1)),
                end = Offset(size.width, size.height - yAxisSpace * (i + 1)),
                strokeWidth = 4f
            )
        }

        for (i in points.indices) {
            val x1 = xAxisSpace * (i + 1)
            val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
            val height = size.height - 100 - y1
            drawRect(
                lineColor,
                topLeft = Offset(x1 - xAxisSpace * 0.3f, y1),
                size = Size(xAxisSpace * 0.6f, if (height >= 0f) height else 0f)
            )
        }
    }
}
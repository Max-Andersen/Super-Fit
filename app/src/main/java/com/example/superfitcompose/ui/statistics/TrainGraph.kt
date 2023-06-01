package com.example.superfitcompose.ui.statistics

import android.graphics.Paint
import android.graphics.PointF
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

/**
 * Created by Saurabh
 */
@Composable
fun TrainGraph(
    modifier: Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int,
    gridColor: Color,
    lineColor: Color,
    pointColor: Color
) {
    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
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
        /** placing x axis points */

        drawLine(
            color = gridColor,
            start = Offset(xAxisSpace, size.height - 100),
            end = Offset(size.width, size.height - 100)
        )

        for (i in xValues.indices) {
            drawContext.canvas.nativeCanvas.drawText(
                "${xValues[i]}",
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

        drawLine(
            color = gridColor,
            start = Offset(100f, 0f),
            end = Offset(100f, size.height - 100),
            strokeWidth = 4f
        )

        /** placing y axis points */
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

        /** placing our x axis points */
        for (i in points.indices) {
            val x1 = xAxisSpace * xValues[i]
            val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
            coordinates.add(PointF(x1, y1))
            /** drawing circles to indicate all the points */

            drawRect(
                lineColor,
                topLeft = Offset(x1 - xAxisSpace * 0.3f, y1),
                size = Size(xAxisSpace * 0.6f, size.height - 100 - y1)
            )
        }
    }
}
package com.example.superfitcompose.ui.statistics

import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun WeightGraph(
    modifier: Modifier,
    xValues: List<String>,
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

            drawLine(
                color = gridColor,
                start = Offset(size.width, 0f),
                end = Offset(size.width, size.height - 95),
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

            drawLine(
                color = gridColor,
                start = Offset(100f, 0f),
                end = Offset(100f, size.height - 95),
                strokeWidth = 4f
            )

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
                val x1 = xAxisSpace * (i + 1)// xValues[i]
                val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
                coordinates.add(PointF(x1, y1))
                drawCircle(
                    color = pointColor,
                    radius = 15f,
                    center = Offset(x1, y1)
                )
            }

            for (i in 1 until coordinates.size) {
                controlPoints1.add(
                    PointF(
                        (coordinates[i].x + coordinates[i - 1].x) / 2,
                        coordinates[i - 1].y
                    )
                )
                controlPoints2.add(
                    PointF(
                        (coordinates[i].x + coordinates[i - 1].x) / 2,
                        coordinates[i].y
                    )
                )
            }

            val stroke = Path().apply {
                reset()
                moveTo(coordinates.first().x, coordinates.first().y)
                Log.d("!!!!", coordinates.size.toString())
                coordinates.forEach {
                    Log.d("!!!!", "${it.x}  ${it.y}")
                }
                for (i in 0 until coordinates.size - 1) {
                    cubicTo(
                        controlPoints1[i].x, controlPoints1[i].y,
                        controlPoints2[i].x, controlPoints2[i].y,
                        coordinates[i + 1].x, coordinates[i + 1].y
                    )
                }
            }

            drawPath(
                stroke,
                color = lineColor,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round
                )
            )
        }
}
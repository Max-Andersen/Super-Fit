package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.models.Training
import com.example.superfitcompose.ui.trainprogress.TrainProgress
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class GetTrainProgressForExerciseTest {

    private val progressCounter = GetTrainProgressForExercise()

    @Test
    fun `1 element test`() {
        val expected = TrainProgress(lastTrain = 10, progress = 100)
        assertEquals(
            expected,
            progressCounter(
                listOf(
                    Training("", TrainingType.RUNNING, repeatCount = 10),
                )
            )
        )
    }

    @Test
    fun `2 elements test`() {
        val expected = TrainProgress(lastTrain = 19, progress = 90)
        assertEquals(
            expected,
            progressCounter(
                listOf(
                    Training("", TrainingType.RUNNING, repeatCount = 10),
                    Training("", TrainingType.RUNNING, repeatCount = 19)
                )
            )
        )
    }

    @Test
    fun `3 elements test`() {
        val expected = TrainProgress(lastTrain = 38, progress = 100)
        assertEquals(
            expected,
            progressCounter(
                listOf(
                    Training("", TrainingType.RUNNING, repeatCount = 10),
                    Training("", TrainingType.RUNNING, repeatCount = 19),
                    Training("", TrainingType.RUNNING, repeatCount = 38)
                )
            )
        )
    }

    @Test
    fun `empty list test`() {
        val expected = TrainProgress(lastTrain = 0, progress = 0)
        assertEquals(
            expected,
            progressCounter(
                listOf()
            )
        )
    }

}
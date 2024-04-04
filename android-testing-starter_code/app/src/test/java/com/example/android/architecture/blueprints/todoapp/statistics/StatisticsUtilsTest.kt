package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.statistics.getActiveAndCompletedStats
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {


    // if there is not completed tasks and one active task
    //then there are 100% active tasks and 0% completed tasks


    @Test
    fun `getActiveTasksStatsNoCompletedReturntsZeroHunderd`() {

        val tasks = listOf<Task>(

            Task("ttl", "desc", false)
        )

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)

    }

    @Test
    fun `distributesTasksPercentageCorrectly`() {

        val tasks = listOf<Task>(

            Task("ttl", "desc", true),
            Task("ttl", "desc", true),
            Task("ttl", "desc", false),
            Task("ttl", "desc", false),
            Task("ttl", "desc", false)


        )

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)

    }

    @Test
    fun `get Active And Completed Stats Empty Returns Zeros`() {

        val tasks = emptyList<Task>()

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)

    }
}

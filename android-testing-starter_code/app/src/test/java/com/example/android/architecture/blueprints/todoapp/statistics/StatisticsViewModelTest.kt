package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class StatisticsViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var statisticsViewModel: StatisticsViewModel
    private lateinit var taskRepository: FakeTaskRepository


    @Before
    fun setup() {
        taskRepository = FakeTaskRepository()
        statisticsViewModel = StatisticsViewModel(taskRepository)
    }

    @Test
    fun loadTasks() {
        mainCoroutineRule.pauseDispatcher()
        statisticsViewModel.refresh()
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))
        mainCoroutineRule.resumeDispatcher()
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))

    }


    @Test
    fun loadStatsticsReturnsAnError() {

        taskRepository.setReturnError(true)

        statisticsViewModel.refresh()
        assertThat(statisticsViewModel.empty.getOrAwaitValue(), `is`(true))
        assertThat(statisticsViewModel.empty.getOrAwaitValue(), `is`(true))

    }
}
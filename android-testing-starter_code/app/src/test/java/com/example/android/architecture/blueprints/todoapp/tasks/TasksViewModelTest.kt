package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.apache.tools.ant.Main
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalMatchers.not


@ExperimentalCoroutinesApi
class TasksViewModelTest {
    private lateinit var tasksViewModel: TasksViewModel

    private lateinit var fakeTaskRepository: FakeTaskRepository


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setupViewModel() {

        fakeTaskRepository = FakeTaskRepository()
        val task1 = Task("SSS", "1")
        fakeTaskRepository.addTasks(task1)
        tasksViewModel = TasksViewModel(fakeTaskRepository)
    }

    @Test
    fun `add new task sets new task event `() {


        tasksViewModel.addNewTask()
        tasksViewModel.newTaskEvent.getOrAwaitValue()

        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(
            value.getContentIfNotHandled(), (not(nullValue()))
        )

    }

    @Test
    fun ` get tasks add view visible `() {
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }
}
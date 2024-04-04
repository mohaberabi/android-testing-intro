package com.example.android.architecture.blueprints.todoapp.task

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.datasource.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragment
import com.example.android.architecture.blueprints.todoapp.tasks.TasksFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@MediumTest
@ExperimentalCoroutinesApi
class TaskFragmentTest {

    private lateinit var repository: TasksRepository


    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository

    }

    @After
    fun tearRepository() {
        ServiceLocator.tearDown()
    }


    @Test
    fun clickTasNavigatesToDetailFragmentOne() {

        runBlockingTest {
            repository.saveTask(Task("ss", "1"))
            repository.saveTask(Task("ss", "ssss"))
            val senario = launchFragmentInContainer<TaskDetailFragment>(Bundle(), R.style.AppTheme)

            val naviontroller = mock(NavController::class.java)
            senario.onFragment {
                Navigation.setViewNavController(it.view!!, naviontroller)

            }

            onView(withId(R.id.tasks_list))
                .perform(
                    RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                        hasDescendant(withText("ss")), click()
                    )
                )
            verify(naviontroller).navigate(
                TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment("1")
            )

        }

    }
}
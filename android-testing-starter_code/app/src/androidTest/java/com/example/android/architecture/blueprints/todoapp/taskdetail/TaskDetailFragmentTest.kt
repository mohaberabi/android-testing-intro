package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.core.R
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.android.architecture.blueprints.todoapp.datasource.FakeAndroidTestRepository
import org.hamcrest.core.IsNot.not

@ExperimentalCoroutinesApi

@RunWith(AndroidJUnit4::class)

@MediumTest
class TaskDetailFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @Test
    fun activeTasksDetailDisplayedInUi() {


        runBlockingTest {
            val activeTask = Task(
                "ACTIVE",
                "ANDROIDX ROCK",
                false
            )

//            val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
//            launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.Theme)
//            onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
//            onView(withId(R.id.task_detail_title_text)).check(matches(withText("Active Task")))
//            onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
//            onView(withId(R.id.task_detail_description_text)).check(matches(withText("AndroidX Rocks")))
//            // and make sure the "active" checkbox is shown unchecked
//            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
//            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))
//            Thread.sleep(2000)
        }
    }

    @After
    fun tearDownDb() {
        runBlockingTest {
            ServiceLocator.tearDown()

        }
    }
}